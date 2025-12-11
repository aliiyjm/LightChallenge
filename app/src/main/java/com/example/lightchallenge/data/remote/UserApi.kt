package com.example.lightchallenge.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// --- Modelos usados en las peticiones y respuestas ---
data class UserDto(
    val username: String,
    val password: String
)

data class UserResponse(
    val message: String,
    val username: String
)

// --- API para registro y login de usuarios ---
interface UserApi {

    // Registrar usuario nuevo
    @POST("register")
    suspend fun register(@Body user: UserDto): Response<UserResponse>

    // Iniciar sesión de usuario existente
    @POST("login")
    suspend fun login(@Body user: UserDto): Response<UserResponse>
}