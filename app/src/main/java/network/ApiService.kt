package com.example.semestral.network

import com.example.semestral.model.LoginRequest
import com.example.semestral.model.LoginResponse
import com.example.semestral.model.RegisterRequest
import com.example.semestral.model.Plato
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Inicio de sesión → devuelve token y datos del usuario
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // Registro de nuevo usuario
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<Void>

    // Obtener menú de platos (requiere token JWT)
    @GET("api/menu")
    suspend fun obtenerMenu(@Header("Authorization") token: String): Response<List<Plato>>

    // Agregar nuevo plato al menú
    @POST("api/menu")
    suspend fun agregarPlato(
        @Header("Authorization") token: String,
        @Body plato: Plato
    ): Response<Void>

    // Actualizar un plato existente
    @PUT("api/menu/{id}")
    suspend fun actualizarPlato(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body plato: Plato
    ): Response<Void>
}
