package com.example.feriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.feriaapp.CatImage
import com.example.feriaapp.ui.theme.FeriaAPPTheme

class CatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeriaAPPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CatScreen()
                }
            }
        }
    }
}

@Composable
fun CatScreen(catViewModel: CatViewModel = viewModel()) {
    val uiState by catViewModel.uiState.collectAsState()

    // Variable para recordar la última lista de imágenes exitosa
    var lastSuccessImages by remember { mutableStateOf<List<CatImage>?>(null) }

    // Actualizar lastSuccessImages cuando el estado sea Success
    LaunchedEffect(uiState) {
        if (uiState is CatUiState.Success) {
            lastSuccessImages = (uiState as CatUiState.Success).catImages
        }
        // Opcional: Limpiar si volvemos a Empty o Error sin datos previos
        // else if (uiState is CatUiState.Empty || (uiState is CatUiState.Error && (uiState as CatUiState.Error)...)) {
        //     lastSuccessImages = null
        // }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { catViewModel.fetchMoreCatImages() },
            enabled = !(uiState is CatUiState.Loading && uiState !is CatUiState.Empty),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            val buttonText = when (val currentState = uiState) {
                is CatUiState.Success -> if (currentState.catImages.isNotEmpty()) "Mostrar más gatitos" else "Mostrar Gatitos"
                is CatUiState.Empty -> "Mostrar Gatitos"
                is CatUiState.Loading -> "Cargando..."
                is CatUiState.Error -> "Reintentar Carga de Gatitos"
            }
            Text(text = buttonText)
        }

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            when (val state = uiState) {
                is CatUiState.Loading -> {
                    if (lastSuccessImages != null && lastSuccessImages!! .isNotEmpty()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                            CatImageList(images = lastSuccessImages!!)
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator()
                            Text(text = "Cargando más gatitos...", modifier = Modifier.padding(top = 8.dp))
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Cargando gatitos...")
                            }
                        }
                    }
                }
                is CatUiState.Success -> {
                    if (state.catImages.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No se encontraron gatitos. ¡Intenta de nuevo!")
                        }
                    } else {
                        CatImageList(images = state.catImages)
                    }
                }
                is CatUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_cat_error_placeholder), // REEMPLAZA
                                contentDescription = "Error",
                                modifier = Modifier.size(120.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is CatUiState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Presiona 'Mostrar Gatitos' para empezar.")
                    }
                }
            }
        }
    }
}
// (Continuación de CatActivity.kt - Asegúrate que esta parte sigue a la PARTE 1 que te di ANTES)

@Composable
fun CatImageList(images: List<CatImage>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio que le da el Box padre en CatScreen
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(images, key = { it.id }) { catImage -> // Usar el id como key para mejor rendimiento
            CatImageItem(catImage = catImage)
        }
    }
}

@Composable
fun CatImageItem(catImage: CatImage) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Usa una altura fija temporalmente en lugar de aspect ratio
            .padding(vertical = 4.dp), // Añade un poco de padding para ver los items
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Centra la imagen si es más pequeña
        ) {
            // Opcional: Mostrar el ID del gato
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(catImage.imageUrl)
                    .crossfade(true)
                    // No placeholders por ahora
                    // No error drawable por ahora
                    .build(),
                contentDescription = "Imagen de un gato con ID: ${catImage.id}",
                modifier = Modifier.fillMaxSize(), // La imagen intentará llenar el Box
                contentScale = ContentScale.Fit    // Fit: Escala manteniendo el aspect ratio,
                // podría no llenar todo el espacio si el aspect ratio no coincide.
                // Pero es más seguro para empezar.
            )

        }
    }
}

// --- Previews ---
// Estas previews están diseñadas para reflejar los diferentes estados de la UI
// que hemos definido y la nueva estructura con lista.

@Preview(showBackground = true, name = "Cat Screen - Empty")
@Composable
fun PreviewCatScreenEmpty() {
    FeriaAPPTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)) {
                Text("Mostrar Gatitos")
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(text = "Presiona 'Mostrar Gatitos' para empezar.")
            }
        }
    }
}

@Preview(showBackground = true, name = "Cat Screen - Loading (Initial)")
@Composable
fun PreviewCatScreenLoadingInitial() {
    FeriaAPPTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {}, enabled = false, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)) {
                Text("Cargando...")
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Cargando gatitos...")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Cat Screen - Loading (More)")
@Composable
fun PreviewCatScreenLoadingMore() {
    FeriaAPPTheme {
        val mockImages = List(3) { index ->
            CatImage(id = "catPrev$index", imageUrl = "https://example.com/catPrev$index.jpg", width = 600, height = 400)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {}, enabled = false, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)) {
                Text("Cargando...")
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                    CatImageList(images = mockImages) // Muestra las imágenes existentes
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                    Text(text = "Cargando más gatitos...", modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Cat Screen - Error")
@Composable
fun PreviewCatScreenError() {
    FeriaAPPTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)) {
                Text("Reintentar Carga de Gatitos")
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cat_error_placeholder), // REEMPLAZA
                        contentDescription = "Error",
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hubo un error al cargar las imágenes.",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720, name = "Cat Screen - Success (List)")
@Composable
fun PreviewCatScreenSuccessWithList() {
    FeriaAPPTheme {
        val mockImages = List(5) { index ->
            CatImage(id = "cat$index", imageUrl = "https://example.com/cat$index.jpg", width = 600, height = 400)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)) {
                Text("Mostrar más gatitos")
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()){
                CatImageList(images = mockImages)
            }
        }
    }
}