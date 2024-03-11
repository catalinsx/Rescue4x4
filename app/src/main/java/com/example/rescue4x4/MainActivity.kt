package com.example.rescue4x4

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rescue4x4.askforhelp.AskForHelp
import com.example.rescue4x4.diagnosis.FAQScreen
import com.example.rescue4x4.ui.theme.Rescue4x4Theme
import com.example.rescue4x4.weatherFiles.Const.Companion.colorBg1
import com.example.rescue4x4.weatherFiles.Const.Companion.colorBg2
import com.example.rescue4x4.weatherFiles.Const.Companion.permissions
import com.example.rescue4x4.weatherFiles.viewmodel.MainViewModel
import com.example.rescue4x4.weatherFiles.viewmodel.STATE
import com.example.rescue4x4.weatherFiles.views.ForecastSection
import com.example.rescue4x4.weatherFiles.views.WeatherSection
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.widgets.ScaleBar
import kotlinx.coroutines.coroutineScope


open class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallBack: LocationCallback
    private var locationRequired: Boolean = false
    private lateinit var mainViewModel: MainViewModel

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallBack.let {
            fusedLocationClient.removeLocationUpdates(it)

        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallBack.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )

        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLocationClient()
        initViewModel()
        setContent {
            var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
            val cameraPositionState: CameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
            }



            locationCallBack = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = LatLng(location.latitude, location.longitude)

                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            currentLocation, cameraPositionState.position.zoom
                        )
                    }
                }
            }

            Rescue4x4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = "Map",
                            modifier = Modifier.weight(1f)
                        ) {
                            composable("Map") {
                                LocationScreen(
                                    context = this@MainActivity,
                                    currentLocation = currentLocation,
                                    cameraPositionState = cameraPositionState
                                )
                            }
                            composable("SOS") { SOSScreen(currentLocation) }
                            composable("More") { MoreScreen(navController) }
                            composable("Weather") {
                                WeatherForm(context = this@MainActivity, currentLocation = currentLocation)
                            }
                            composable("Diagnosis") { FAQScreen() }
                            composable("AskForHelp") { AskForHelp(currentLocation, this@MainActivity) }
                        }
                        NavigationBarTest(navController = navController)
                    }
                }
            }
        }
    }

    private fun fetchWeatherInformation(mainViewModel: MainViewModel, currentLocation: LatLng) {
        mainViewModel.state = STATE.LOADING
        mainViewModel.getWeatherByLocation(currentLocation)
        mainViewModel.getForecastByLocation(currentLocation)
        mainViewModel.state = STATE.SUCCESS
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }

    private fun initLocationClient() {
        fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(this)
    }


    @Composable
    fun LocationScreen(
        context: Context,
        currentLocation: LatLng,
        cameraPositionState: CameraPositionState,
    ) {
        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
        var properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
        var isSatelliteViewEnabled by remember { mutableStateOf(false) }
        var mapLoaded by remember { mutableStateOf(false) }


        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionMaps ->
            val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                locationRequired = true
                startLocationUpdates()
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        Column {
            if (!mapLoaded) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color =Color(colorBg2),
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    properties = properties,
                    uiSettings = uiSettings,
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
                        if (permissions.all {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    it
                                ) == PackageManager.PERMISSION_GRANTED
                            }) {
                            startLocationUpdates()
                            mapLoaded = true
                        } else {
                            launcherMultiplePermissions.launch(permissions)
                        }

                    }
                ) {
                    Marker(state = MarkerState(position = currentLocation))
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                            text = "Satellite Mode",
                            color = if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface,
                            fontStyle = FontStyle.Italic
                        )
                        Switch(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 0.dp),
                            checked = isSatelliteViewEnabled,
                            onCheckedChange = {
                                isSatelliteViewEnabled = it
                                properties =
                                    properties.copy(mapType = if (it) MapType.SATELLITE else MapType.NORMAL)
                            },
                            thumbContent = {
                                Icon(
                                    imageVector = if(isSatelliteViewEnabled){
                                        Icons.Default.Check
                                    }else{
                                        Icons.Default.Close
                                    },
                                    contentDescription = null
                                )
                            }
                        )
                    }
                    ScaleBar(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(8.dp),
                        textColor =  if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface,
                        lineColor =  if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }

    @Composable
    fun WeatherForm(context: Context, currentLocation: LatLng ) {

        var weatherFetched by remember { mutableStateOf(false) }

        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionMaps ->
            val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                locationRequired = true
                startLocationUpdates()
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        val systemUiController = rememberSystemUiController()
        DisposableEffect(key1 = true, effect = {
            systemUiController.isSystemBarsVisible = false //hide status bar
            onDispose {
                systemUiController.isSystemBarsVisible = true // show status bar
            }
        })
        LaunchedEffect(key1 = currentLocation, block = {
            coroutineScope {
                if (permissions.all{
                        ContextCompat.checkSelfPermission(context, it) ==PackageManager.PERMISSION_GRANTED
                    }){
                    startLocationUpdates()
                }else{
                    launcherMultiplePermissions.launch(permissions)
                }
            }
        })

        LaunchedEffect(key1 = true, block = {
            if (!weatherFetched) {
                fetchWeatherInformation(mainViewModel, currentLocation)
                weatherFetched = true
            }
        })

        val gradient = Brush.linearGradient(
            colors = listOf(Color(colorBg1), Color(colorBg2)),
            start = Offset(1000f, -1000f),
            end = Offset(1000f, 1000f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient),
        ){
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val marginTop = screenHeight * 0.1f
            val marginTopPx = with(LocalDensity.current) {marginTop.toPx()}

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        // define the layout for child

                        layout(
                            placeable.width,
                            placeable.height + marginTopPx.toInt()
                        ) {
                            placeable.placeRelative(0, marginTopPx.toInt())
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (mainViewModel.state) {
                    STATE.LOADING -> {
                        LoadingSection()
                    }
                    STATE.FAILED -> {
                        ErrorSection(mainViewModel.errorMessage)
                    }
                    else -> {
                        WeatherSection(mainViewModel.weatherResponse)
                        ForecastSection(mainViewModel.forecastResponse)
                    }
                }
            }
        }
    }

    @Composable
    fun ErrorSection(errorMessage: String) {
        return Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorMessage, color = Color.White)
        }
    }

    @Composable
    fun LoadingSection() {
        return Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
