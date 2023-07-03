package com.example.todolist.db.dao

import androidx.lifecycle.LiveData
import com.example.todolist.db.model.Todo

interface IToDoDao {

    suspend fun insertTodoData(toDo: Todo)

    fun getTodoData() : LiveData<List<Todo>>

    suspend fun deleteTodoData(toDo: Todo)

    suspend fun deleteAllTodoData()

    suspend fun updateTodoData(toDo: Todo)
}