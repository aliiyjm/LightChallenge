package com.example.lightchallenge.data.repository

import com.example.lightchallenge.data.local.UserDao
import com.example.lightchallenge.data.local.UserEntity
import com.example.lightchallenge.data.remote.UserApi
import com.example.lightchallenge.data.remote.UserDto

class UserRepository(
    private val userDao: UserDao,
    private val userApi: UserApi
) {
    suspend fun registerUserLocal(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun loginLocal(username: String, password: String): UserEntity? {
        return userDao.login(username, password)
    }

    suspend fun registerRemote(user: UserEntity) {
        userApi.register(UserDto(user.username, user.password))
    }

    suspend fun loginRemote(user: UserEntity): Boolean {
        return try {
            val response = userApi.login(UserDto(user.username,
                user.password))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCurrentUser(): UserEntity? = userDao.getCurrentUser()

    suspend fun logout() = userDao.clearUsers()
}