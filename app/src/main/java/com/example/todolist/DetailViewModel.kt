package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.TodoDatabase.Companion.getDatabase
import com.example.todolist.db.TodoRepository
import com.example.todolist.db.model.TagData
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TodoRepository(application)

    val tagList: LiveData<List<TagData>> = repository.getAllTags()

    fun insertTag(tagDao: TagData) {
        viewModelScope.launch {
            repository.insertTag(tagDao)
        }
    }
}