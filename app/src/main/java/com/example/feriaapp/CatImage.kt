package com.example.feriaapp

import com.squareup.moshi.Json
// import com.squareup.moshi.JsonClass // No es estrictamente necesario para moshi-kotlin con reflexión

// @JsonClass(generateAdapter = true) // Necesario si usas moshi-kotlin-codegen con KSP/KAPT
data class CatImage(
    @Json(name = "id") val id: String,
    @Json(name = "url") val imageUrl: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int
)