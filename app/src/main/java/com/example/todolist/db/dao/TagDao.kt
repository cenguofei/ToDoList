package com.example.todolist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todolist.db.model.Tag
import com.example.todolist.db.model.TagData

@Dao
interface TagDao : ITagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertTag(tag: TagData)

    @Query("SELECT * FROM tag_table")
    override fun getAllTags(): LiveData<List<TagData>>

    @Delete
    override suspend fun deleteTag(tagData: TagData)
}