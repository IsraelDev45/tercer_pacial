package com.example.feriaapp // O el paquete donde lo hayas creado

import androidx.compose.ui.geometry.isEmpty
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// Asegúrate que estas importaciones sean correctas según la ubicación de tus archivos
import com.example.feriaapp.CatImage
import com.example.feriaapp.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException // Para manejar errores de red
import retrofit2.HttpException // Para manejar errores HTTP

// Definimos un sealed interface para representar los diferentes estados de la UI
sealed interface CatUiState {
    data class Loading(val previousImages: List<CatImage>? = null) : CatUiState // Modificado
    data class Success(val catImages: List<CatImage>) : CatUiState
    data class Error(val message: String, val previousImages: List<CatImage>? = null) : CatUiState // Opcional: también para Error
    data object Empty : CatUiState

}


class CatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<CatUiState>(CatUiState.Empty)
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    // Lista interna para acumular todas las imágenes de gatos cargadas
    private val _loadedCatImages = mutableListOf<CatImage>()

    private val catApiService = RetrofitInstance.api
    private val imagesPerRequest = 10 // Cuántas imágenes pedir cada vez

    /**
     * Obtiene un nuevo lote de imágenes de gatos y las añade a la lista existente.
     */
    fun fetchMoreCatImages() {
        // Cuando empezamos a cargar, pasamos las imágenes actuales si existen
        _uiState.value = CatUiState.Loading(previousImages = if (_loadedCatImages.isNotEmpty()) _loadedCatImages.toList() else null)

        viewModelScope.launch {
            try {
                val newImageList = catApiService.getRandomCatImage(limit = imagesPerRequest)

                if (newImageList.isNotEmpty()) {
                    _loadedCatImages.addAll(newImageList)
                    _uiState.value = CatUiState.Success(_loadedCatImages.toList())
                } else {
                    // Si no se obtuvieron nuevas imágenes
                    if (_loadedCatImages.isEmpty()) {
                        // Si no había nada antes y no se cargó nada nuevo
                        _uiState.value = CatUiState.Error("No se encontraron imágenes de gatos.")
                    } else {
                        // Si ya teníamos imágenes, simplemente volvemos al estado Success con las existentes
                        // (esto podría pasar si la API no tiene más imágenes nuevas para ofrecer)
                        _uiState.value = CatUiState.Success(_loadedCatImages.toList())
                        // Opcionalmente, podrías emitir un mensaje diferente aquí si quieres
                        // notificar al usuario que "no hay más imágenes nuevas por ahora".
                    }
                }
            } catch (e: IOException) {
                // Si hay un error de red y ya teníamos imágenes, podríamos optar por mostrar
                // las imágenes existentes en lugar de ir directamente a un estado de error completo.
                // Por ahora, lo mantenemos simple: cualquier error de carga va a Error.
                if (_loadedCatImages.isNotEmpty()) {
                    _uiState.value = CatUiState.Error("Error de red al cargar más. Mostrando anteriores. (${e.localizedMessage ?: "Error desconocido"})")
                    // O podrías hacer: _uiState.value = CatUiState.Success(_loadedCatImages.toList())
                    // y mostrar un Toast o Snackbar con el error.
                } else {
                    _uiState.value = CatUiState.Error("Error de red: ${e.localizedMessage ?: "Error desconocido"}")
                }
            } catch (e: HttpException) {
                if (_loadedCatImages.isNotEmpty()) {
                    _uiState.value = CatUiState.Error("Error HTTP ${e.code()} al cargar más. Mostrando anteriores. (${e.message()})")
                } else {
                    _uiState.value = CatUiState.Error("Error HTTP ${e.code()}: ${e.message()}")
                }
            } catch (e: Exception) {
                if (_loadedCatImages.isNotEmpty()) {
                    _uiState.value = CatUiState.Error("Error desconocido al cargar más. Mostrando anteriores. (${e.localizedMessage ?: "Error inesperado"})")
                } else {
                    _uiState.value = CatUiState.Error("Error desconocido: ${e.localizedMessage ?: "Error inesperado"}")
                }
            }
        }
    }

    // Cargar el primer lote de imágenes al iniciar el ViewModel.
    // Si prefieres que el usuario presione el botón la primera vez, comenta este bloque init.
    init {
        fetchMoreCatImages()
    }
}