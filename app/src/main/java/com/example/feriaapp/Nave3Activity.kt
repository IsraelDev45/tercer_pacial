package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class Nave3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaveDetailScreen(  // Cambiado de NaveScreen a NaveDetailScreen
                title = "Nave 3: Tecnología e Innovación",
                imageRes = R.drawable.nave_3,  // Cambiado de nave3_detail a nave_3
                description = "Explora lo último en tecnología e innovación en nuestra Nave 3. " +
                        "Descubre startups locales, desarrollos tecnológicos y soluciones " +
                        "innovadoras que están transformando nuestra región.",
                onBackPressed = { finish() }
            )
        }
    }
}