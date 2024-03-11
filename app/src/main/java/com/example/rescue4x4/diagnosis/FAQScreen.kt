package com.example.rescue4x4.diagnosis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(){
    LazyColumn{
        items(faqList){ faqItem ->
            var expanded by remember { mutableStateOf(false)}

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    expanded = !expanded
                }
            ){
                Column(modifier = Modifier.padding(16.dp)){
                    Text(
                        text = faqItem.question,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    if(expanded){
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = faqItem.answer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}