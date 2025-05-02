package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

class ImportantDatesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondScreen(onBackPressed = { finish() })
        }
    }
}

@Composable
fun SecondScreen(onBackPressed: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DateItem("15 de Octubre", "Inauguración de la feria")
            DateItem("20 de Octubre", "Concierto principal")
            DateItem("25 de Octubre", "Feria gastronómica")
            DateItem("30 de Octubre", "Clausura y fuegos artificiales")

            Button(
                onClick = onBackPressed,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Volver",
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

@Composable
fun DateItem(date: String, event: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6650a4)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = date,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Text(
                text = event,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
        }
    }
}