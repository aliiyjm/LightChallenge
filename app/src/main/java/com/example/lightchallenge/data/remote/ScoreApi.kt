package com.example.lightchallenge.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class ScoreDto(
    val playerName: String,
    val score: Int,
    val date: String,
    val username: String
)

data class ScoreResponse(
    val id: Int,
    val playerName: String,
    val score: Int,
    val date: String,
    val username: String
)

interface ScoreApi {

    @POST("scores")
    suspend fun postScore(@Body score: ScoreDto): Response<Map<String, Any>>

    @GET("scores/{username}")
    suspend fun getUserScores(@Path("username") username: String):
            Response<List<ScoreResponse>>
}