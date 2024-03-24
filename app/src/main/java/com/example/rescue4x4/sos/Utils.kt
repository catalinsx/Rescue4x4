package com.example.rescue4x4.sos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

class Utils {
    companion object {
        fun sendHelpMessage(context: Context, currentLocation: LatLng) {
            val locationUrl = "https://maps.google.com/?q=${currentLocation.latitude},${currentLocation.longitude}"
            val message = "I'm stuck, injured or in a very dangerous situation, it's not a joke. I am sending you my location: $locationUrl"

            val numbers = arrayOf("0740184743") // am fac array ca sa pot trimite mesaj la mai multe numere
            val smsManager = SmsManager.getDefault()

            for (number in numbers) {
                smsManager.sendTextMessage(number, null, message, null, null)
            }
            Toast.makeText(context, "Message sent!", Toast.LENGTH_SHORT).show()
        }

        @Composable
        fun Share(text: String, context: Context){
            // cream un intent pentru a trimite un mesaj
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                // se adauga textul, unde se specifica typeul textului
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            // si se mai creeaza un intent unde sa alegem din diverse aplicatii unde sa l trimitem
            val shareIntent = Intent.createChooser(sendIntent, null)
            Button(onClick = {
                ContextCompat.startActivity(context, shareIntent, null)
            } ){
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
                Text(modifier = Modifier.padding(start = 8.dp), text = "Share")
            }
        }

        fun callEmergency(context: Context) {
            // cream un obiect de tip Intent pentru efectuare unui apel telefonic
            val intent = Intent(Intent.ACTION_CALL)
            // specificam numarul de telefon cu ajutorul URI
            intent.data = Uri.parse("tel:0740184743")
            // pornim activitatea acestui intent
            context.startActivity(intent)
        }

        fun openContacts(context: Context) {
            // cream un obiect de tip Intent care deschide contactele
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            // pornim intentul si afiseaza lista
            ContextCompat.startActivity(context, intent, null)
        }
    }
}