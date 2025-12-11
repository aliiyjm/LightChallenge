package com.example.lightchallenge.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lightchallenge.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val lux by viewModel.lux.collectAsState()
    val score by viewModel.score.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val isPermissionGranted by viewModel.isSensorPermissionGranted.collectAsState()
    val context = LocalContext.current

    // Diálogo de permiso simulado
    if (!isPermissionGranted) {
        AlertDialog(
            onDismissRequest = { /* No permitir cerrar sin aceptar o rechazar explícitamente */ },
            title = { Text("Permiso Requerido") },
            text = { Text("Esta aplicación necesita acceder al sensor de luz ambiental para funcionar. ¿Deseas permitir el acceso?") },
            confirmButton = {
                Button(onClick = { viewModel.grantSensorPermission() }) {
                    Text("Permitir")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    // Cerrar la aplicación
                    if (context is Activity) {
                        context.finish()
                    }
                }) {
                    Text("No permitir")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Usuario: ${currentUser?.username ?: "Invitado"}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isPermissionGranted) {
            Text(
                text = " Luz ambiental: ${"%.1f".format(lux)} lux",
                style = MaterialTheme.typography.headlineSmall
            )
        } else {
            Text(
                text = "Permiso de sensor pendiente",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Puntuación: $score",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {  viewModel.saveScore()
            },
            enabled = currentUser != null
        ) {
            Text("Guardar Puntuación")
        }

        if (currentUser == null) {
            Text(
                "Inicia sesión para guardar tu puntuación",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}