package com.example.todolist.fragment.main

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.db.model.Todo

class ToDoDiffUtil(
    private val newData:List<Todo>,
    private val oldData:List<Todo>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldData.size
    }
    override fun getNewListSize(): Int {
        return newData.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem == newItem
    }
}