package com.example.todolist.db.dao

import androidx.lifecycle.LiveData
import com.example.todolist.db.model.Tag
import com.example.todolist.db.model.TagData

interface ITagDao {

    suspend fun insertTag(tag: TagData)

    fun getAllTags() : LiveData<List<TagData>>

    suspend fun deleteTag(tagData: TagData)
}