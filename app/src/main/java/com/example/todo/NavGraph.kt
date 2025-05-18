package com.example.todo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.ui.screens.TodoListScreen
import com.example.todo.ui.screens.TodoDetailScreen
import com.example.todo.viewmodel.TodoListViewModel
import com.example.todo.viewmodel.TodoDetailViewModel

@Composable
fun AppNavGraph(navController: NavHostController, listVM: TodoListViewModel, detailVM: TodoDetailViewModel) {
    NavHost(navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(viewModel = listVM) { id ->
                navController.navigate("detail/$id")
            }
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            TodoDetailScreen(viewModel = detailVM, id = id) {
                navController.popBackStack()
            }
        }
    }
}
