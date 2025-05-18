package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.local.TodoDatabase
import com.example.todo.data.remote.TodoApiService
import com.example.todo.data.repository.TodoRepository
import com.example.todo.viewmodel.TodoListViewModel
import com.example.todo.viewmodel.TodoDetailViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = TodoDatabase.getDatabase(this)
        val repository = TodoRepository(
            Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TodoApiService::class.java),
            db.todoDao()
        )

        val listViewModel = TodoListViewModel(repository)
        val detailViewModel = TodoDetailViewModel(repository)

        setContent {
            val navController = rememberNavController()
            AppNavGraph(navController, listViewModel, detailViewModel)
        }
    }
}
