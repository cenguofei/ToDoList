package com.example.todolist.db.converter

import androidx.room.TypeConverter
import com.example.todolist.db.model.Priority

class TodoConverter {
    /**
     * 将Priority对象转为String
     */
    @TypeConverter
    fun priorityToString(priority: Priority) : String = priority.name

    /**
     * 从数据库读取出来之后转为Priority对象
     */
    @TypeConverter
    fun stringToPriority(string: String) : Priority =
        Priority.valueOf(string)
}