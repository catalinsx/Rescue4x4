package com.example.rescue4x4.more

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rescue4x4.R

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MoreScreen(
        navController: NavController
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = {
                    navController.navigate("Weather")
                }
            ) {
                Box(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.weatherphoto),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    )
                    Text(
                        text = "Check the weather",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 26.sp
                    )
                }
            }


            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = {
                    navController.navigate("AskForHelp")
                }
            ) {
                Box(
                    modifier = Modifier.padding(8.dp),
                ) {

                    Image(
                        painter = painterResource(R.drawable.raptor1),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    )
                    Text(
                        text = "Ask for help",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 25.sp

                    )
                }
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = {
                    navController.navigate("Diagnosis")
                }
            ) {
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {

                    Image(
                        painter = painterResource(R.drawable.mechanic),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    )
                    Text(
                        text = "Diagnosis",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 26.sp
                    )
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = {
                    navController.navigate("Supply")
                }
            ) {
                Box(
                    modifier = Modifier.padding(8.dp),
                ) {

                    Image(
                        painter = painterResource(R.drawable.notepad),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    )
                    Text(
                        text = "Supply List",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 25.sp

                    )
                }
            }
        }
    }

@SuppressLint("SuspiciousIndentation")
@Composable
@Preview(showBackground = true)
fun MoreScreenPreview(){
  val navController = rememberNavController()
    MoreScreen(navController = navController)
}