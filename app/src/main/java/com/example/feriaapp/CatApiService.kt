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
@GET("v1/images/search")
suspend fun getRandomCatImage(
    @Query("limit") limit: Int = 1,
): List<CatImage>


}