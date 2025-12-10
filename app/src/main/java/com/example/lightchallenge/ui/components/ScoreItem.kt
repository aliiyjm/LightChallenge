package com.example.lightchallenge.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lightchallenge.data.local.ScoreEntity
@Composable
fun ScoreItem(
    score: ScoreEntity,
    onDelete: (ScoreEntity) -> Unit,
    onEdit: (ScoreEntity) -> Unit
)  {
    var showEditDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text("
                👤
            ${score.playerName}", style =
            MaterialTheme.typography.titleMedium)
            Text("Puntuación: ${score.score}", style =
                MaterialTheme.typography.bodyMedium)
            Text("
                📅
            ${score.date}", style =
            MaterialTheme.typography.bodySmall)
        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { showEditDialog = true }) {
                Text("Editar") }
            Button(
                onClick = { onDelete(score) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Eliminar")
            }
        }
    }

    //
    �
    �
    Diálogo de edición (solo nombre)
    if (showEditDialog) {
        EditNameDialog(
            score = score,
            onDismiss = { showEditDialog = false },
            onSave = {
                onEdit(it)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun EditNameDialog(
    score: ScoreEntity,
    onDismiss: () -> Unit,
    onSave: (ScoreEntity) -> Unit
) {
    var newName by remember { mutableStateOf(score.playerName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar nombre del jugador") },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Nuevo nombre") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = {
                if (newName.isNotBlank()) {
                    onSave(score.copy(playerName = newName))
                }
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}