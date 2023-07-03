package com.example.todolist.fragment.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.model.Priority
import com.example.todolist.db.model.Todo

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {
    private var dataList:List<Todo> = emptyList()

    class MyViewHolder(view:View) :RecyclerView.ViewHolder(view){
        private var dateTextView:TextView = itemView.findViewById(R.id.date)
        private var priorityImageView:ImageView = itemView.findViewById(R.id.priority)
        private var titleTextView:TextView = itemView.findViewById(R.id.toDotitle)
        private var timeTextView:TextView = itemView.findViewById(R.id.ctime)
        private var tagTextView:TextView = itemView.findViewById(R.id.tag)

        //使用静态方法封装创建MyViewHolder的方法
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(
                    R.layout.todo_item_layout,parent,false)
                return MyViewHolder(view)
            }
        }
        //绑定数据
        @SuppressLint("SetTextI18n")
        fun bindData(todo: Todo){
            //标题
            titleTextView.text = todo.title
            //标签
            tagTextView.text = todo.tag.text
            tagTextView.backgroundTintList = ColorStateList
                .valueOf(Color.parseColor(todo.tag.bgColor))
            //优先级
            when(todo.priority){
                Priority.HIGH -> priorityImageView.setImageResource(R.drawable.red_plate)
                Priority.MIDDLE->priorityImageView.setImageResource(R.drawable.yellow_plate)
                Priority.LOW->priorityImageView.setImageResource(R.drawable.green_plate)
            }
            //时间
            dateTextView.text = "${todo.date.month+1}.${todo.date.day}"

            //添加点击事件
            itemView.setOnClickListener {
                val action = MainFragmentDirections
                    .actionMainFragmentToDetailFragment(todo)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (dataList.isNotEmpty()) {
            holder.bindData(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(newData: List<Todo>){
        val diffResult = DiffUtil.calculateDiff(ToDoDiffUtil(newData,dataList))
        dataList = newData
        diffResult.dispatchUpdatesTo(this)
    }
}