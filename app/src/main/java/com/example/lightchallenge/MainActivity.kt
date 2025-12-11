package com.example.lightchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lightchallenge.ui.screens.*
import com.example.lightchallenge.ui.theme.LightChallengeTheme
import com.example.lightchallenge.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LightChallengeTheme {
                val viewModel: GameViewModel = viewModel()
                val currentUser by viewModel.currentUser.collectAsState()

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (currentUser == null) {
                        AuthNavigator(viewModel)
                    } else {
                        GameNavigator(viewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun AuthNavigator(viewModel: GameViewModel) {
    var showLogin by remember { mutableStateOf(true) }

    if (showLogin) {
        LoginScreen(
            viewModel = viewModel,
            onLoginSuccess = { /* Ya manejado por currentUser en MainActivity */ },
            onRegisterClick = { showLogin = false }
        )
    } else {
        RegisterScreen(
            viewModel = viewModel,
            onRegisterSuccess = {
                // Al registrarse con éxito, mostramos el login
                showLogin = true
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameNavigator(viewModel: GameViewModel) {
    var showGame by remember { mutableStateOf(true) }

    Column {
        TopAppBar(
            title = { Text("Light Challenge") },
            actions = {
                TextButton(onClick = { viewModel.logout() }) {
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.primary)
                }
            }
        )

        if (showGame) {
            Button(
                onClick = { showGame = false },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Ver puntuaciones")
            }
            GameScreen(viewModel)
        } else {
            Button(
                onClick = { showGame = true },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Jugar")
            }
            ScoreListScreen(viewModel)
        }
    }
}