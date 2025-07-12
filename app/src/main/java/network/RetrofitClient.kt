package com.example.semestral.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Dirección base del backend. Reemplázala por tu IP o dominio real.
    // Ejemplo para emulador de Android Studio accediendo a localhost:
    // http://10.0.2.2:8000/
    private const val BASE_URL = "http://192.168.0.2:8000"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
