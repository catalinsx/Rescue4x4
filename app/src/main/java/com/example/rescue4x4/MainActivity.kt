package com.example.rescue4x4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rescue4x4.ui.theme.Rescue4x4Theme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Rescue4x4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val locationInfo = LocationData(46.77238770, 24.66945610)
                    val navController = rememberNavController()
                   Column(modifier = Modifier.fillMaxSize()
                    ) {
                       NavigationBarTest(navController = navController)
                       NavHost(navController = navController, startDestination = "Map") {
                           composable("Map") { MapScreen(location = locationInfo) }
                           composable("SOS") { SOSScreen() }
                           composable("More") { MoreScreen() }
                       }
                   }
                }
            }
        }
    }
}

data class NavigationItem(val label: String, val icon: ImageVector)

@Composable
fun NavigationBarTest(navController: NavController){
    var selectedItem by remember { mutableStateOf("Map") }
    val items = listOf(
        NavigationItem("Map", Icons.Filled.LocationOn),
        NavigationItem("SOS", Icons.Filled.Call),
        NavigationItem("More", Icons.Filled.Menu)
    )


    Column(verticalArrangement = Arrangement.Bottom) {
        NavigationBar {
            items.forEach {item ->
                val selected = selectedItem == item.label
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = selected,
                    onClick = {
                        selectedItem = item.label
                        navController.navigate(item.label)
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NavigationPreview(){
    val navController = rememberNavController()
    NavigationBarTest(navController)
}