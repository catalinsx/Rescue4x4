package com.example.rescue4x4.more.askforhelp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescue4x4.R
import com.google.android.gms.maps.model.LatLng


@Composable
fun AskForHelp(currentLocation: LatLng, context: Context){
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var fullName by remember { mutableStateOf("")}
        var phone by remember { mutableStateOf("")}
        var county by remember { mutableStateOf("")}
        var city by remember { mutableStateOf("")}
        var landmarks by remember { mutableStateOf("")}
        var information1 by remember { mutableStateOf("")}
        var information2 by remember { mutableStateOf("")}

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                "Important!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                stringResource(id = R.string.askforhelpheader),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(8.dp)
          ){
              Row(modifier = Modifier.padding(8.dp)){
                  Icon(
                      imageVector = Icons.Filled.Call,
                      contentDescription = "Contact"
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text("Contact")
              }
              TextField(
                  value = fullName,
                  onValueChange = {fullName = it},
                  label = { Text(text = "Full Name")},
                  modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(8.dp))
              )
              Spacer(modifier = Modifier.padding(8.dp))
              TextField(
                  value = phone,
                  onValueChange = {phone = it},
                  label = { Text(text = "Phone Number")},
                  modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(8.dp))
              )

          }
        }

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){
                Row(modifier = Modifier.padding(8.dp)){
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Location")
                }
                TextField(
                    value = county,
                    onValueChange = {county = it},
                    label = { Text(text = "County")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = city,
                    onValueChange = {city = it},
                    label = { Text(text = "The city or village where you are located")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = landmarks,
                    onValueChange = {landmarks = it},
                    label = { Text(text = "Specify landmarks or information about the location where you are and how to reach you")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = TextFieldValue(text = currentLocation.latitude.toString()),
                    onValueChange = { },
                    label = { Text(text = " ")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = TextFieldValue(text = currentLocation.longitude.toString()),
                    onValueChange = { },
                    label = { Text(text = " ")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){
                Row(modifier = Modifier.padding(8.dp)){
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Information"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Information")
                }
                TextField(
                    value = information1,
                    onValueChange = {information1 = it},
                    label = { Text(text = stringResource(R.string.information1))},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = information2,
                    onValueChange = {information2 = it},
                    label = { Text(text = stringResource(R.string.information2))},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )

            }
        }


        Spacer(modifier = Modifier.height(8.dp))
        ElevatedButton(onClick = {
            sendEmail(
                context = context,
                fullName = fullName,
                phone = phone,
                county = county,
                city = city,
                landmarks = landmarks,
                information1 = information1,
                information2 = information2,
                latitude = currentLocation.latitude,
                longitude = currentLocation.longitude
            )
        }) {
            Row(modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.Default.Email ,
                    contentDescription = "Email"
                )
                Text(
                    text = "Send it",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

    fun sendEmail(
        context: Context,
        fullName: String,
        phone: String,
        county: String,
        city: String,
        landmarks: String,
        information1: String,
        information2: String,
        latitude: Double,
        longitude: Double
    ) {
        // create an intent to send an email
        val emailIntent = Intent(Intent.ACTION_VIEW).apply {
            // uri is a string of characters that identifies a resource
            // in this case the uri indicates that the intent is meant to handle emails
            // the apply function is used to set the properties of the intent
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("help@rescue4x4.ro"))
            putExtra(Intent.EXTRA_SUBJECT, "Help Request from $county")
            putExtra(
                Intent.EXTRA_TEXT,
                "Full Name: $fullName\n" +
                        "Phone Number: $phone\n" +
                        "County: $county\n" +
                        "City: $city\n" +
                        "Landmarks: $landmarks\n" +
                        "Information: $information1\n" +
                        "Other information: $information2\n" +
                        "Latitude: $latitude\n" +
                        "Longitude: $longitude\n"
            )
        }
        try {
            context.startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }
