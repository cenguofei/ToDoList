package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.TodoDatabase.Companion.getDatabase
import com.example.todolist.db.TodoRepository
import com.example.todolist.db.model.Todo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    //数据仓库
    private val repository = TodoRepository(application)

    //todo数据
    val todoDataList: LiveData<List<Todo>> = repository.getTodoData()

    /**                  todoDao                 */
    /**
     * 插入todo
     */
    fun insertTodoData(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodoData(todo)
        }
    }

    /**
     * 删除一条todo
     */
    fun deleteTodoData(todo: Todo) {
        viewModelScope.launch {
            repository.deleteTodoData(todo)
        }
    }

    /**
     * 清空todo
     */
    fun deleteAllTodoData() {
        viewModelScope.launch {
            repository.deleteAllTodoData()
        }
    }

    fun updateTodoData(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodoData(todo)
        }
    }
}