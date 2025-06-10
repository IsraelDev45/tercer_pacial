package com.example.feriaapp


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor // Para ver logs de las peticiones (opcional, útil para debug)

object RetrofitInstance {

    private const val BASE_URL = "https://api.thecatapi.com/"

    // Opcional: Interceptor para logging (muy útil durante el desarrollo)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Puedes elegir .NONE, .BASIC, .HEADERS, .BODY
    }

    // Opcional: Cliente OkHttp personalizado para añadir interceptores, timeouts, etc.
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Añade el interceptor de logging
        // .connectTimeout(30, TimeUnit.SECONDS) // Ejemplo de timeout de conexión
        // .readTimeout(30, TimeUnit.SECONDS)    // Ejemplo de timeout de lectura
        .build()

    // Configuración de Moshi para parsear JSON a objetos Kotlin
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Importante para que Moshi funcione bien con Kotlin
        .build()

    // Creación perezosa (lazy) de la instancia de Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient) // Usa el cliente OkHttp personalizado (opcional, pero bueno para logging)
            // Si no quieres un cliente OkHttp personalizado, puedes omitir .client(httpClient)
            // y Retrofit usará uno por defecto.
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Usa Moshi para la conversión
            .build()
    }

    // Creación perezosa del servicio de la API
    val api: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}