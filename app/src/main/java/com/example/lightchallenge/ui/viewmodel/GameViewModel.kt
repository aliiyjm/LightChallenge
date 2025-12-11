package com.example.lightchallenge.ui.viewmodel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightchallenge.data.local.*
import com.example.lightchallenge.data.remote.ScoreApi
import com.example.lightchallenge.data.remote.UserApi
import com.example.lightchallenge.data.repository.ScoreRepository
import com.example.lightchallenge.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class GameViewModel(application: Application) :
    AndroidViewModel(application), SensorEventListener {

    // SharedPreferences para controlar el "permiso" de primer uso
    private val prefs = application.getSharedPreferences("light_challenge_prefs", Context.MODE_PRIVATE)
    
    // Estado del permiso
    private val _isSensorPermissionGranted = MutableStateFlow(prefs.getBoolean("sensor_permission_granted", false))
    val isSensorPermissionGranted: StateFlow<Boolean> = _isSensorPermissionGranted

    // Sensor de luz
    private val sensorManager = application.getSystemService(SensorManager::class.java)
    private val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    private var currentScore = 0
    private val threshold = 15f

    private val _lux = MutableStateFlow(0f)
    val lux: StateFlow<Float> = _lux

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _scoresList = MutableStateFlow<List<ScoreEntity>>(emptyList())
    val scoresList: StateFlow<List<ScoreEntity>> = _scoresList

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    private val repository: ScoreRepository
    private val userRepository: UserRepository

    init {
        val db = ScoreDatabase.getDatabase(application)
        val dao = db.scoreDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/") // acceso a FastAPI local desde emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val scoreApi = retrofit.create(ScoreApi::class.java)
        val userApi = retrofit.create(UserApi::class.java)


        repository = ScoreRepository(dao, scoreApi)
        userRepository = UserRepository(db.userDao(), userApi)

        viewModelScope.launch {
            _currentUser.value = userRepository.getCurrentUser()
        }

        // Si ya se concedió el permiso anteriormente, iniciamos el sensor
        if (_isSensorPermissionGranted.value) {
            startSensor()
        }
        
        loadScores()
    }

    // ---------------- PERMISO SIMULADO ----------------
    fun grantSensorPermission() {
        prefs.edit().putBoolean("sensor_permission_granted", true).apply()
        _isSensorPermissionGranted.value = true
        startSensor()
    }

    // ---------------- SENSOR ----------------
    fun startSensor() {
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        _lux.value = event.values[0]
        if (event.values[0] >= threshold) {
            currentScore++
            _score.value = currentScore
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // ---------------- SCORES ----------------
    fun saveScore() {
        val user = _currentUser.value ?: return
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val scoreEntity = ScoreEntity(
            playerName = user.username,
            score = currentScore,
            date = date,
            username = user.username
        )
        viewModelScope.launch {
            repository.insertScore(scoreEntity)
            _scoresList.value = repository.getAllScores()
            repository.syncScore(scoreEntity)
        }
        currentScore = 0
        _score.value = 0
    }

    fun deleteScore(score: ScoreEntity) {
        viewModelScope.launch {
            repository.deleteScore(score)
            _scoresList.value = repository.getAllScores()
        }
    }

    fun updateScore(updatedScore: ScoreEntity) {
        viewModelScope.launch {
            repository.updateScore(updatedScore)
            _scoresList.value = repository.getAllScores()
        }
    }

    private fun loadScores() {
        viewModelScope.launch {
            _scoresList.value = repository.getAllScores()
        }
    }

    // ---------------- USER ----------------
    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            try {
                userRepository.registerUserLocal(user)
                userRepository.registerRemote(user)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.loginLocal(username, password)
            if (user != null) {
                _currentUser.value = user
                onResult(true)
            } else {
                try {
                    val response = userRepository.loginRemote(UserEntity(username = username, password = password))
                    if (response) {
                        val newUser = UserEntity(username = username, password = password)
                        userRepository.registerUserLocal(newUser)
                        _currentUser.value = newUser
                        onResult(true)
                    } else onResult(false)
                } catch (e: Exception) {
                    onResult(false)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _currentUser.value = null
        }
    }
}