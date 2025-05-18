package com.example.todo.data.repository

import com.example.todo.data.local.TodoDao
import com.example.todo.data.model.Todo
import com.example.todo.data.remote.TodoApiService

class TodoRepository(
    private val apiService: TodoApiService,
    private val todoDao: TodoDao
) {
    suspend fun fetchTodos(): List<Todo> {
        return try {
            val todos = apiService.getTodos()
            todoDao.insertTodos(todos)
            todos
        } catch (e: Exception) {
            todoDao.getAllTodos()
        }
    }

    suspend fun getTodoById(id: Int): Todo? = todoDao.getTodoById(id)
}
