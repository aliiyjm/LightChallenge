package com.example.lightchallenge.data.repository

import com.example.lightchallenge.data.local.ScoreDao
import com.example.lightchallenge.data.local.ScoreEntity
import com.example.lightchallenge.data.remote.ScoreApi
import com.example.lightchallenge.data.remote.ScoreDto

class ScoreRepository(
    private val dao: ScoreDao,
    private val api: ScoreApi
) {

    // ROOM CRUD
    suspend fun insertScore(score: ScoreEntity) = dao.insert(score)
    suspend fun updateScore(score: ScoreEntity) = dao.update(score)
    suspend fun deleteScore(score: ScoreEntity) = dao.delete(score)
    suspend fun getAllScores() = dao.getAllScores()

    // Retrofit
    suspend fun syncScore(score: ScoreEntity) {
        val dto = ScoreDto(
            playerName = score.playerName,
            score = score.score,
            date = score.date,
            username = score.username
        )
        api.postScore(dto)
    }
}