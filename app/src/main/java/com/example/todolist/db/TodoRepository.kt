package com.example.todolist.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todolist.db.TodoDatabase.Companion.getDatabase
import com.example.todolist.db.model.TagData
import com.example.todolist.db.model.Todo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(
    context:Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val todoDatabase = context.getDatabase()
    private val todoDao = todoDatabase.getTodoDao()
    private val tagDao = todoDatabase.getTagDao()

    /**                  todoDao                 */
    //插入todo
    suspend fun insertTodoData(todo: Todo) =
        withContext(dispatcher) { todoDao.insertTodoData(todo) }

    //获取todo
    fun getTodoData(): LiveData<List<Todo>> = todoDao.getTodoData()

    //删除一条todo
    suspend fun deleteTodoData(todo: Todo) =
        withContext(dispatcher) { todoDao.deleteTodoData(todo) }

    //清空todo
    suspend fun deleteAllTodoData() = withContext(dispatcher) { todoDao.deleteAllTodoData() }

    //更新
    suspend fun updateTodoData(todo: Todo) =
        withContext(dispatcher) { todoDao.updateTodoData(todo) }

    /**                   tagDao                   */
    //插入标签
    suspend fun insertTag(tagData: TagData) = withContext(dispatcher) { tagDao.insertTag(tagData) }

    //读取所有标签
    fun getAllTags(): LiveData<List<TagData>> = tagDao.getAllTags()

    //删除tag
    suspend fun deleteTag(tagData: TagData) = withContext(dispatcher) { tagDao.deleteTag(tagData) }
}