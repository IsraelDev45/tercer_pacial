package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.content.Intent
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(
                onNavigateToSecondActivity = {
                    startActivity(Intent(this, ImportantDatesActivity::class.java))
                },
                onNavigateToNave1 = {
                    startActivity(Intent(this, Nave1Activity::class.java))
                },
                onNavigateToNave2 = {
                    startActivity(Intent(this, Nave2Activity::class.java))
                },
                onNavigateToNave3 = {
                    startActivity(Intent(this, Nave3Activity::class.java))
                },
                onNavigateToArtists = {
                    startActivity(Intent(this, ArtistsActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun MainScreen(
    onNavigateToSecondActivity: () -> Unit,
    onNavigateToNave1: () -> Unit,
    onNavigateToNave2: () -> Unit,
    onNavigateToNave3: () -> Unit,
    onNavigateToArtists: () -> Unit
) {
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
            // Lista de negocios con sus imágenes
            BusinessItem(
                title = "Nave 1: Gastronomía Local",
                description = "Explora los sabores tradicionales de nuestra región",
                imageRes = R.drawable.nave_1,
                cardColor = Color(0xFF6650a4),
                onClick = onNavigateToNave1
            )

            BusinessItem(
                title = "Nave 2: Artesanías",
                description = "Productos artesanales y manualidades",
                imageRes = R.drawable.nave_2,
                cardColor = Color(0xFF6650a4),
                onClick = onNavigateToNave2
            )

            BusinessItem(
                title = "Nave 3: Tecnología e Innovación",
                description = "Lo último en tecnología y emprendimiento",
                imageRes = R.drawable.nave_3,
                cardColor = Color(0xFF6650a4),
                onClick = onNavigateToNave3
            )

            BusinessItem(
                title = "Artistas Invitados",
                description = "Conoce a los artistas que nos visitan este año",
                imageRes = R.drawable.artistas,
                cardColor = Color(0xFFD0BCFF),
                onClick = onNavigateToArtists
            )

            // Botón para navegar a la segunda actividad
            Button(
                onClick = onNavigateToSecondActivity,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Fechas importantes",
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

@Composable
fun BusinessItem(
    title: String,
    description: String,
    imageRes: Int,
    cardColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Imagen de $title",
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        onNavigateToSecondActivity = {},
        onNavigateToNave1 = {},
        onNavigateToNave2 = {},
        onNavigateToNave3 = {},
        onNavigateToArtists = {}
    )
}