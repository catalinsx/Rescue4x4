package com.example.rescue4x4.sos

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescue4x4.R
import com.example.rescue4x4.sos.Utils.Companion.Share
import com.example.rescue4x4.sos.Utils.Companion.callEmergency
import com.example.rescue4x4.sos.Utils.Companion.openContacts
import com.example.rescue4x4.sos.Utils.Companion.sendHelpMessage
import com.google.android.gms.maps.model.LatLng


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOSScreen(currentLocation: LatLng) {
    var showDialog2 by remember { mutableStateOf(false)}
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current



    /*
    rememberLauncherForActivityResult este e o functie din Compose ce creaza un launcher care primeste
    doua argumente, un ActivityResultContracts.RequestPermission care cere o singura permsiune dar prima data
    verifica daca e data permisiunea respectiva sau daca nu, o cere.
     */

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            callEmergency(context)
        }
    }

    val requestPermissionLauncherForContacts = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openContacts(context)
        }
    }
    val requestPermissionLauncherForSMS = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            sendHelpMessage(context, currentLocation)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState()),
       horizontalAlignment = Alignment.CenterHorizontally,
       // verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Latitude: ${currentLocation.latitude}",
            fontSize = 32.sp
        )
        Text(
            text = "Longitude: ${currentLocation.longitude}",
            fontSize = 32.sp
        )
        Share(
            text =  "Here is my location data: Latitude: ${currentLocation.latitude} /" +
                    " Longitude: ${currentLocation.longitude}" +
                    " Click on the link to see my location on Google Maps, send this to someone: " +
                    "https://maps.google.com/?q=${currentLocation.latitude},${currentLocation.longitude}",
            context = context)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.title),
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        ExpandableText(
            text = stringResource(id = R.string.paragraph),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedCard (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
                .fillMaxWidth()
                .height(100.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = {
                requestPermissionLauncherForSMS.launch(android.Manifest.permission.SEND_SMS)
                  //  showDialog2 = true // dialogul asta e sossignalu dialog care nu merge data storeu
            }

        ){
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.alertgood),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Send an SOS signal",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }

        // dialogul pentru add persons, o sa fac totul hardcodat cu cateva persoane
//        if (showDialog2) {
//                AddPersonDialog(
//                    storeData = StoreData(context)
//                ) { showDialog2 = false }
//        }

        Row {
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    showDialog = showDialog.not()
                }
            ){
                Text(
                    text = "Call 112",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(R.drawable.emergencycall),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(170.dp)
                        .padding(20.dp)

                )
            }

            if(showDialog){
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
                            showDialog = false
                        }) {
                            Text("Continue")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    title = {Text("You are about to call the emergency service, are you sure?")},
                )
            }

            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    requestPermissionLauncherForContacts.launch(android.Manifest.permission.READ_CONTACTS)
                }
            ){
                Text(
                    text = "Call a friend",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(R.drawable.call),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(170.dp)
                        .padding(20.dp)
                )
            }

        }
    }
}


@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp,
    maxLines: Int = 5,
   color: Color = MaterialTheme.colorScheme.primary
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            modifier = Modifier.clickable { expanded = !expanded },
            textAlign = textAlign,
            fontSize = fontSize,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            color = color
        )
       Row{
           if (!expanded) {
               Icon(
                   imageVector = Icons.Default.KeyboardArrowDown,
                   contentDescription = "Show more",
                   tint = MaterialTheme.colorScheme.tertiary,
                   modifier = Modifier.clickable { expanded = true }
               )
               Text(
                   text = "Show more",
                   color = MaterialTheme.colorScheme.tertiary,
                   modifier = Modifier.clickable { expanded = true }
               )
           } else {
               Icon(
                   imageVector = Icons.Default.KeyboardArrowUp,
                   contentDescription = "Show less",
                   tint = MaterialTheme.colorScheme.tertiary,
                   modifier = Modifier.clickable { expanded = false }
               )
               Text(
                   text = "Show less",
                   color = MaterialTheme.colorScheme.tertiary,
                   modifier = Modifier.clickable { expanded = false }
               )
           }
       }
    }
}



@Preview(showBackground = false)
@Composable
fun SOSScreenPreview()
{
    val location = remember { LatLng(0.0, 0.0) }
    SOSScreen(currentLocation = location)
}
