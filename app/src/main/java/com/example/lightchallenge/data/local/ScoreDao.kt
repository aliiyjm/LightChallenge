package com.example.lightchallenge.data.local

import androidx.room.*
import androidx.room.Dao

@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(score: ScoreEntity)

    @Update
    suspend fun update(score: ScoreEntity)

    @Delete
    suspend fun delete(score: ScoreEntity)

    @Query
    suspend fun getAllScores(): List<ScoreEntity>


}