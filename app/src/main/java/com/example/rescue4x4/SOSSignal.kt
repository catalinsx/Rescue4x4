package com.example.rescue4x4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Person(
    val name: String,
    val phoneNumber: String
)

@Composable
fun AddPersonDialog(
    persons: MutableList<Person>,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add a contact") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") }
                )
                DisplayPersonList(persons = persons)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phoneNumber.isNotBlank()) {
                        persons.add(Person(name, phoneNumber))
                        name = ""
                        phoneNumber = ""
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DisplayPersonList(persons: MutableList<Person>) {
   Column {
       persons.forEach { person ->
           Row(
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically,
           ) {

               Text(
                   "${person.name}: ${person.phoneNumber}",
                   fontSize = 18.sp
               )
               IconButton(onClick = { persons.remove(person) }) {
                   Icon(Icons.Default.Delete, contentDescription = "Delete")
               }
           }
       }
   }
}

