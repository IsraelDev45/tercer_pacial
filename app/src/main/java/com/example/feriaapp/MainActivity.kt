package com.example.feriaapp

import android.content.Intent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Asegúrate de que tu tema de Compose esté aquí si lo tienes definido
            // FeriaAPPTheme {
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
                }, // <--- ESTA COMA FALTABA AQUÍ ANTES DE onNavigateToCatScreen
                onNavigateToCatScreen = {
                    startActivity(Intent(this, CatActivity::class.java))
                }
            )
            // }
        }
    }
}

@Composable
fun MainScreen(
    onNavigateToSecondActivity: () -> Unit,
    onNavigateToNave1: () -> Unit,
    onNavigateToNave2: () -> Unit,
    onNavigateToNave3: () -> Unit,
    onNavigateToArtists: () -> Unit,
    onNavigateToCatScreen: () -> Unit // Parámetro añadido correctamente
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
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre todos los ítems
        ) {
            BusinessItem(
                title = "Nave 1",
                description = "Explora los Juegos de nuestra región",
                imageRes = R.drawable.nave_1, // Asegúrate que este drawable exista
                cardColor = Color(0xFFE5904C),
                onClick = onNavigateToNave1
            )

            BusinessItem(
                title = "Nave 2: Artesanías y relajación",
                description = "Productos artesanales y Lugares para relajarse",
                imageRes = R.drawable.nave_2, // Asegúrate que este drawable exista
                cardColor = Color(0xFFE5904C),
                onClick = onNavigateToNave2
            )

            BusinessItem(
                title = "Nave 3: Playa",
                description = "Admira las playas de nuestra Nave 3",
                imageRes = R.drawable.nave_3, // Asegúrate que este drawable exista
                cardColor = Color(0xFFE5904C),
                onClick = onNavigateToNave3
            )

            BusinessItem(
                title = "Artistas",
                description = "Conoce a los artistas",
                imageRes = R.drawable.artistas, // Asegúrate que este drawable exista
                cardColor = Color(0xFFE5904C),
                onClick = onNavigateToArtists
            )

            // Opción 1: Usar BusinessItem para "Ver Gatitos" (consistente con tu diseño)
            BusinessItem(
                title = "¡Gatitos!",
                description = "Unos cuantos gatitos bonitos",
                imageRes = R.drawable.ic_cat_placeholder, // ¡¡NECESITAS AÑADIR ESTE DRAWABLE!!
                cardColor = Color(0xFFE5904C), // Ejemplo de color, puedes cambiarlo
                onClick = onNavigateToCatScreen
            )

            // Opción 2: Usar un Button simple para "Ver Gatitos" (como lo tenías antes)
            // Si prefieres esta opción, comenta el BusinessItem de arriba y descomenta este Button.
            /*
            Button(
                onClick = onNavigateToCatScreen,
                modifier = Modifier.fillMaxWidth() // Para que ocupe el ancho como los BusinessItem
                                     // .padding(top = 0.dp) // Ajusta el padding si es necesario
            ) {
                Text(
                    text = "Ver Gatitos",
                    fontFamily = FontFamily.SansSerif
                )
            }
            */

            Button(
                onClick = onNavigateToSecondActivity,
                modifier = Modifier.fillMaxWidth() // Para que ocupe el ancho
            ) {
                Text(
                    text = "Las fechas importantes",
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
    imageRes: Int, // Asegúrate de que los drawables referenciados aquí existan
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
    // Asegúrate de que tu tema de Compose esté aquí si lo tienes definido
    // FeriaAPPTheme {
    MainScreen(
        onNavigateToSecondActivity = {},
        onNavigateToNave1 = {},
        onNavigateToNave2 = {},
        onNavigateToNave3 = {},
        onNavigateToArtists = {},
        onNavigateToCatScreen = {} // Parámetro añadido correctamente para el Preview
    )
    // }
}