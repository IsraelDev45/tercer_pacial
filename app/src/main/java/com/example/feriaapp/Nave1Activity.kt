package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class Nave1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaveDetailScreen(
                title = "Nave 1: Diverciones",
                imageRes = R.drawable.nave_1,
                description = "La Nave 1 está en focada en los parques de diverciones como los juegos mecanicos y demas",
                onBackPressed = { finish() }
            )
        }
    }
}