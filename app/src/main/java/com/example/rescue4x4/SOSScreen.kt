package com.example.rescue4x4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.currentCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOSScreen(currentLocation: LatLng) {
    var showDialog by remember { mutableStateOf(false)}
    val context = LocalContext.current
    val contactLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri ->
        contactUri?.let {
            val intent = Intent(Intent.ACTION_CALL, it)
            context.startActivity(intent)
        }
    } // nu merge inca sa suni, ma baga in contacte dar inca nu merge de sunat
    // trebuie sa cer si permisiuni
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
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
            text = "Here is my location data: Latitude: ${currentLocation.latitude} /" +
                    " Longitude: ${currentLocation.longitude}",
            context = context)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.title),
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(id = R.string.paragraph),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(id = R.string.secondparagraph),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary),
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
                    painter = painterResource(R.drawable.emergency_call),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(170.dp)
                        .padding(20.dp)
                )
            }

            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary),
                onClick = {}
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
                    painter = painterResource(R.drawable.add_contact),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(170.dp)
                        .padding(20.dp)
                )
            }
        }
        if(showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                                TextButton(onClick = { /*TODO*/ }) {
                                    Text("Continue")
                                    // aici sa fac sa sune la 112
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

    }
}
@Composable
fun Share(text: String, context: Context){
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    Button(onClick = {
        startActivity(context, shareIntent, null)
    } ){
        Icon(imageVector = Icons.Default.Share, contentDescription = null)
        Text(modifier = Modifier.padding(start = 8.dp), text = "Share")
    }
}

@Preview
@Composable
fun SOSScreenPreview()
{
    val location = remember { LatLng(0.0, 0.0) }
    SOSScreen(currentLocation = location)
}
