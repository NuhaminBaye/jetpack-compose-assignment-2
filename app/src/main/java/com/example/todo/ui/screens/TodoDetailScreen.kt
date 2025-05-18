package com.example.todo.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.viewmodel.TodoDetailViewModel
import androidx.compose.ui.Alignment
import com.example.todo.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.graphics.Color

@Composable
fun TodoDetailScreen(viewModel: TodoDetailViewModel, id: Int, onBack: () -> Unit) {
    val todo by viewModel.todo.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadTodoById(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        todo?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = grayish
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Todo Details",
                            style = MaterialTheme.typography.titleLarge,
                            color = textPrimary
                        )
                        Icon(
                            imageVector = if (it.completed) 
                                Icons.Filled.CheckCircle 
                            else 
                                Icons.Filled.Clear,
                            contentDescription = if (it.completed) "Completed" else "Pending",
                            tint = if (it.completed) turquoise else textSecondary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Divider(color = textSecondary.copy(alpha = 0.2f))
                    Text(
                        text = "ID: ${it.id}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = textPrimary
                    )
                    Text(
                        text = "User ID: ${it.userId}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = textPrimary
                    )
                    Text(
                        text = "Title: ${it.title}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = textPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Status: ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = textPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (it.completed) "Completed" else "Pending",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (it.completed) turquoise else textSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = bright
                        ),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Back", color = Color.White)
                    }
                }
            }
        } ?: CircularProgressIndicator(color = bright)
    }
}
