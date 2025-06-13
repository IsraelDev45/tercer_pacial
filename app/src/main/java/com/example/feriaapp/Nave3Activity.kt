package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class Nave3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaveDetailScreen(  // Cambiado de NaveScreen a NaveDetailScreen
                title = "Nave 3: Playa",
                imageRes = R.drawable.nave_3,  // Cambiado de nave3_detail a nave_3
                description = "Explora los hermosos cielos en la playa de nuestra Nave 3. " +
                        "Descubre las comidad y vebidas mas exoticas en nuestros bares " +
                        "y restaurantes lujosos.",
                onBackPressed = { finish() }
            )
        }
    }
}