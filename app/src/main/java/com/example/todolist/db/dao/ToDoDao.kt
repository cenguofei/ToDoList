package com.example.todolist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.db.model.Todo

@Dao
interface ToDoDao : IToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertTodoData(toDo: Todo)

    @Query("SELECT * FROM todo_table")
    override fun getTodoData(): LiveData<List<Todo>>

    @Delete
    override suspend fun deleteTodoData(toDo: Todo)

    @Query("DELETE FROM todo_table")
    override suspend fun deleteAllTodoData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun updateTodoData(toDo: Todo)
}