package com.example.todo.data.remote

import com.example.todo.data.model.Todo
import retrofit2.http.GET

interface TodoApiService {
    @GET("/todos")
    suspend fun getTodos(): List<Todo>
}
