package com.example.todo.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.viewmodel.TodoListViewModel
import com.example.todo.viewmodel.TodoUiState
import androidx.compose.ui.Alignment
import com.example.todo.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon

@Composable
fun TodoListScreen(viewModel: TodoListViewModel, onItemClick: (Int) -> Unit) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        when (state) {
            is TodoUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = bright)
                }
            }

            is TodoUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Error: ${(state as TodoUiState.Error).message}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = textPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadTodos() },
                        colors = ButtonDefaults.buttonColors(containerColor = bright)
                    ) {
                        Text("Retry", color = Color.White)
                    }
                }
            }

            is TodoUiState.Success -> {
                val todos = (state as TodoUiState.Success).todos
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(todos) { todo ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = grayish
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemClick(todo.id) },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = todo.title,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = textPrimary
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (todo.completed) 
                                                Icons.Filled.CheckCircle 
                                            else 
                                                Icons.Filled.Clear,
                                            contentDescription = if (todo.completed) "Completed" else "Pending",
                                            tint = if (todo.completed) turquoise else textSecondary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (todo.completed) "Completed" else "Pending",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (todo.completed) turquoise else textSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
