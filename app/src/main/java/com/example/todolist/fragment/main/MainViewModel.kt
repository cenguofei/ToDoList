package com.example.todolist.fragment.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.TodoRepository
import com.example.todolist.db.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application):AndroidViewModel(application) {
    //数据仓库
    private val repository = TodoRepository(application)
    //todo数据
    val todoDataList:LiveData<List<Todo>> = repository.getTodoData()

    fun insertTodoData(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertTodoData(todo)
        }
    }
    //删除一条todo
    fun deleteTodoData(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTodoData(todo)
        }
    }
    //删除所有todo
    fun deleteAllTodoData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllTodoData()
        }
    }

    fun updateTodoData(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTodoData(todo)
        }
    }
}