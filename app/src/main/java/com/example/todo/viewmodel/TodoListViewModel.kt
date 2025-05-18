package com.example.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.model.Todo
import com.example.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TodoUiState {
    object Loading : TodoUiState()
    data class Success(val todos: List<Todo>) : TodoUiState()
    data class Error(val message: String) : TodoUiState()
}

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _state = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val state: StateFlow<TodoUiState> = _state

    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            _state.value = TodoUiState.Loading
            try {
                val todos = repository.fetchTodos()
                _state.value = TodoUiState.Success(todos)
            } catch (e: Exception) {
                _state.value = TodoUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun loadTodos() {
        fetchTodos()
    }
}
