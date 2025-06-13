package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class Nave2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaveDetailScreen( // Cambiado de NaveScreen a NaveDetailScreen
                title = "Nave 2: Artesanías y relajación",
                imageRes = R.drawable.nave_2, // Usando nave_2 como nombre de imagen
                description = "La Nave 2 exhibe las mejores artesanías de la región, " +
                        "con productos elaborados por artesanos locales que preservan " +
                        "las técnicas tradicionales y lugares para relajarse y disfrutar.",
                onBackPressed = { finish() }
            )
        }
    }
}