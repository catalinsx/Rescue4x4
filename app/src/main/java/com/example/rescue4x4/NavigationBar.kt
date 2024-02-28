package com.example.rescue4x4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

data class NavigationItem(val label: String, val icon: ImageVector)

@Composable
fun NavigationBarTest(navController: NavController){
    var selectedItem by remember { mutableStateOf("") }
    val items = listOf(
        NavigationItem("Map", Icons.Filled.LocationOn),
        NavigationItem("SOS", Icons.Filled.Call),
        NavigationItem("More", Icons.Filled.Menu)
    )

    Column(verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.zIndex(1f)) {
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
