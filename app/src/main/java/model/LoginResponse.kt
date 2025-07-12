package com.example.semestral.model

data class LoginResponse(
    val token: String,
    val usuario: Usuario
)

data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String
)
