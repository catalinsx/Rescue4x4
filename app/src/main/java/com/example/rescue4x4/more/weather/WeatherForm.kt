package com.example.rescue4x4.more.weather

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.rescue4x4.maps.LocationUtils
import com.example.rescue4x4.more.weather.Fetcher.fetchWeatherInformation
import com.example.rescue4x4.more.weather.Fetcher.mainViewModel
import com.example.rescue4x4.more.weather.viewmodel.MainViewModel
import com.example.rescue4x4.more.weather.viewmodel.STATE
import com.example.rescue4x4.more.weather.views.ForecastSection
import com.example.rescue4x4.more.weather.views.WeatherSection
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.coroutineScope


object Fetcher{
    lateinit var mainViewModel: MainViewModel
    fun fetchWeatherInformation(mainViewModel: MainViewModel, currentLocation: LatLng) {
        mainViewModel.state = STATE.LOADING
        mainViewModel.getWeatherByLocation(currentLocation)
        mainViewModel.getForecastByLocation(currentLocation)
        mainViewModel.state = STATE.SUCCESS
    }
}

@Composable
fun WeatherForm(context: Context, currentLocation: LatLng) {

    var weatherFetched by remember { mutableStateOf(false) }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMaps ->
        val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            LocationUtils.locationRequired = true
            LocationUtils.startLocationUpdates()
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    // launched effect sunt folosite pentru a face lucruri asincrone, cum ar fi fetch-ul de date
    // in timp ce se incarca pagina... deci mai creeaza un thread in background
    LaunchedEffect(key1 = currentLocation, block = {
        coroutineScope {
            if (Const.permissions.all{
                    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }){
                LocationUtils.startLocationUpdates()
            }else{
                launcherMultiplePermissions.launch(Const.permissions)
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
        colors = listOf(Color(Const.colorBg1), Color(Const.colorBg2)),
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