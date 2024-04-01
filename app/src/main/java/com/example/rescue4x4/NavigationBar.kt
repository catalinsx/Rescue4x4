package com.example.rescue4x4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController

/*
un data class e o clasa care are create toate metodele ce teoretic trebuie sa le suprascrii tu
inclusiv si gettere si settere, e folosita la lista, cel putin asta am facut eu
 */
data class NavigationItem(val label: String, val icon: ImageVector)

@Composable
fun NavigationBarTest(
    navController: NavController
){
    // mutableStateOf e pentru a actualiza starea in timp real a unui element in ui
    // e folosit pentru a activa recompozitia, iar rememeber e utilizat pentru a retine valoarea
    // pe toata durata lifecycle lui
    var selectedItem by remember { mutableStateOf("Map") }
    val items = listOf(
        NavigationItem("Map", Icons.Filled.LocationOn),
        NavigationItem("SOS", Icons.Filled.Call),
        NavigationItem("More", Icons.Filled.Menu)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        NavigationBar {
            // are loc o functie lambda pt fiecare item din items sa se faca urmatorul lucru...
            items.forEach {item ->
                val selected = selectedItem == item.label
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            selectedItem = item.label
                            navController.navigate(item.label)
                        }
                    }
                )
            }
        }
    }
}
