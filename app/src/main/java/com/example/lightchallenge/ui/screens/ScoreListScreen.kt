package com.example.lightchallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lightchallenge.ui.components.ScoreItem
import com.example.lightchallenge.ui.viewmodel.GameViewModel

@Composable
fun ScoreListScreen(viewModel: GameViewModel) {
    val scores by viewModel.scoresList.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "🏆 Puntuaciones registradas",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(8.dp))

        if (scores.isEmpty()) {
            Text("No hay puntuaciones guardadas todavía.")
        } else {
            LazyColumn {
                items(scores, key = { it.id }) { score ->
                    ScoreItem(
                        score = score,
                        onDelete = { viewModel.deleteScore(it) },
                        onEdit = { viewModel.updateScore(it) } // 👈 actualizar nombre
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}