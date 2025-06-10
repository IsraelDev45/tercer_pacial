package com.example.feriaapp

import com.example.feriaapp.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

/**
 * Obtiene una lista de imágenes de gatos.
 * TheCatAPI devuelve un array JSON, incluso si solo pedimos una imagen.
 * Por eso el tipo de retorno es List<CatImage>.
 *
 * @param limit El número de imágenes a obtener.
 * @param apiKey Tu clave de API para TheCatAPI (opcional, pero recomendada para evitar límites de tasa).
Puedes registrarte en https://thecatapi.com/signup para obtener una.
 *               Si la usas, deberás añadirla como un Query parameter.
 */
@GET("v1/images/search") // Este es el endpoint específico de la API
suspend fun getRandomCatImage(
    @Query("limit") limit: Int = 1, // Por defecto, pedimos 1 imagen
    // @Query("api_key") apiKey: String = "TU_API_KEY_AQUI" // Descomenta y añade tu API Key si la tienes
): List<CatImage> // La API devuelve un array, incluso para una sola imagen con limit=1

    // Podrías añadir más funciones aquí para otros endpoints de TheCatAPI si los necesitas
    // Por ejemplo, para buscar por raza, categoría, etc.
    // @GET("v1/images/search")
    // suspend fun searchImages(
    //     @Query("breed_ids") breedId: String,
    //     @Query("limit") limit: Int = 10,
    //     @Query("api_key") apiKey: String = "TU_API_KEY_AQUI"
    // ): List<CatImage>
}