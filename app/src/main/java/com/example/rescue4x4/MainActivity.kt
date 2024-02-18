package com.example.rescue4x4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.rescue4x4.ui.theme.Rescue4x4Theme
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "Map") {
                        composable("Map") { MapScreen() }
                        composable("SOS") { SOSScreen() }
                        composable("More") { MoreScreen() }
                    }
                    NavigationBarTest(navController)
                }
            }
        }
    }
}


@Composable
fun NavigationBarTest(navController: NavController){
    var selectedItem by remember { mutableStateOf("Map") }
    val items = listOf("Map", "SOS", "More")



    Column(
        verticalArrangement = Arrangement.Bottom
    ){
        NavigationBar {
            items.forEach {item ->
                val selected = selectedItem == item
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = item) },
                    label = { Text(item) },
                    selected = selected,
                    onClick = {
                        selectedItem = item
                        navController.navigate(item)
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