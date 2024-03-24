package com.example.rescue4x4.more.diagnosis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescue4x4.more.diagnosis.DTCCodes.Companion.searchDtcDescription
import com.example.rescue4x4.more.diagnosis.FAQ.Companion.checkAndDisplayFaq
import com.example.rescue4x4.more.diagnosis.FAQ.Companion.deserializeFaqJson

@Composable
fun DTCScreen() {
    var dtcCode by remember { mutableStateOf("") }
    var dtcDescription by remember { mutableStateOf<String?>("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val faqData = deserializeFaqJson(context)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
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
                text = "This page is dedicated to those who want to identify their car problem " +
                        "by themselves, they also have the posibility to call for a rescue team",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )
        }
        OutlinedTextField(
            value = dtcCode,
            onValueChange = {
                if (it.length <= 5)
                    dtcCode = it
            },
            label = { Text("Enter DTC") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                capitalization = KeyboardCapitalization.Characters
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    dtcDescription = searchDtcDescription(context, dtcCode)
                    if (dtcDescription == null)
                        errorMessage = "Description not found for DTC code: $dtcCode"
                }
            ),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (dtcDescription != null) {
            dtcDescription?.let { description ->
                Text(
                    text = description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
                val matchingQuestions = checkAndDisplayFaq(faqData, description)
                DisplayMatchingQuestions(matchingQuestions)
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Text(text = errorMessage, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DisplayMatchingQuestions(matchingQuestions: List<FaqItem>) {
    LazyColumn {
        items(matchingQuestions) { faqItem ->
            var expanded by remember { mutableStateOf(false) }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = { expanded = !expanded }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = faqItem.question,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (expanded) {
                        Text(
                            text = faqItem.answer,
                            fontSize = 18.sp
                        )
                        EmergencyQuestion {

                        }
                    }

                    Row(horizontalArrangement = Arrangement.End) {
                        if (!expanded) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Show more",
                                tint = Color.White,
                                modifier = Modifier.clickable { expanded = true }
                            )
                            Text(
                                text = "Show more",
                                color = Color.White,
                                modifier = Modifier.clickable { expanded = true }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Show less",
                                tint = Color.White,
                                modifier = Modifier.clickable { expanded = false }
                            )
                            Text(
                                text = "Show less",
                                color = Color.White,
                                modifier = Modifier.clickable { expanded = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyQuestion(onClick: () -> Unit) {
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you consider it an emergency?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
        }

        if (isChecked) {
          Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
          ){
              Text(
                  text = "Send a message to the towing company or rescue service " +
                          "to come pick you up by pressing the button below",
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold
              )
              Button(
                  onClick = {},
                  modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
              ) {
                  Icon(
                      imageVector = Icons.Filled.MailOutline,
                      contentDescription = "Add"
                  )
              }
          }
        }
    }
}


