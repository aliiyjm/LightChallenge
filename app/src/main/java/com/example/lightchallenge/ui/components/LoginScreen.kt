package com.example.lightchallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lightchallenge.ui.viewmodel.GameViewModel

@Composable
fun LoginScreen(viewModel: GameViewModel, onLoginSuccess: () -> Unit, onRegisterClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style =
            MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            viewModel.login(username, password) { success ->
                if (success) onLoginSuccess() else error = "Credenciales incorrectas"
            }
        }) {
            Text("Entrar")
        }

        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes cuenta? Regístrate")
        }

        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}