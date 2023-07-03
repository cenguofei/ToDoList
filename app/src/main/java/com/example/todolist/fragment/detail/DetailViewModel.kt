package com.example.todolist.fragment.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.TodoDatabase.Companion.getDatabase
import com.example.todolist.db.TodoRepository
import com.example.todolist.db.model.TagData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application):AndroidViewModel(application) {
    private val repo = TodoRepository(application)
    var tagList:LiveData<List<TagData>> = repo.getAllTags()

    fun insertTag(tagData: TagData){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertTag(tagData)
        }
    }
}