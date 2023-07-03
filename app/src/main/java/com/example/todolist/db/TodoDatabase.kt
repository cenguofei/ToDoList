package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.db.converter.TodoConverter
import com.example.todolist.db.dao.TagDao
import com.example.todolist.db.dao.ToDoDao
import com.example.todolist.db.model.TagData
import com.example.todolist.db.model.Todo

@TypeConverters(TodoConverter::class)
@Database(
    entities = [Todo::class, TagData::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase(){

    abstract fun getTodoDao(): ToDoDao

    abstract fun getTagDao(): TagDao

    //创建单例多项
    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun Context.getDatabase() : TodoDatabase =
            INSTANCE ?: synchronized(TodoDatabase::class) {
                INSTANCE ?: Room.databaseBuilder(
                    this,
                    TodoDatabase::class.java, "todo.db"
                ).build().also { INSTANCE = it }
            }
    }
}