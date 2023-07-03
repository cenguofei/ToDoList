package com.example.todolist.fragment.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.FragmentMainBinding
import com.example.todolist.db.model.Priority
import com.example.todolist.db.model.Todo
import com.example.todolist.dp2px
import com.example.todolist.showToast

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: TodoAdapter by lazy { TodoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        mainViewModel.todoDataList.observe(viewLifecycleOwner){
            //设置提示状态的变化
            checkEmptyStatus(it)

            if (it.isNotEmpty()){
                //第一条数据拿出来显示在头部
                initTopTodo(it[0])
            }
            if (it.size > 1) {
                //把第一条后面的数据跟新到Adapter中
                //包含fromIndex 不包含toIndex
                mAdapter.setData(it.subList(1,it.size))
            }else{
                mAdapter.setData(emptyList())
            }
        }
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }

        val bag = ResourcesCompat.getDrawable(resources, R.drawable.bag, null)
        bag?.let {
            it.setBounds(0,0, dp2px(22),dp2px(22))
            binding.addBtn.setCompoundDrawables(bag,null,null,null)
        }

        binding.topTaskContainer.setOnClickListener {
            //获取第一个数据
            val data = mainViewModel.todoDataList.value!![0]
            val action = MainFragmentDirections
                .actionMainFragmentToDetailFragment(data)
            findNavController().navigate(action)
        }
        binding.deleteAllBtn.setOnClickListener {
            mainViewModel.deleteAllTodoData()
            showToast(requireContext(),"Delete All Success!!")
        }
    }
    /** 初始化头部信息 */
    @SuppressLint("SetTextI18n")
    private fun initTopTodo(todo: Todo){
        binding.topTitle.text = todo.title
        binding.topTime.text = "${todo.date.year}.${todo.date.month+1}.${todo.date.day}"
        binding.topDescription.text = todo.description
        binding.topTag.text = todo.tag.text
        binding.topTag.background.setTint(Color.parseColor(todo.tag.bgColor))
        when(todo.priority){
            Priority.HIGH -> binding.topPriority.setImageResource(R.drawable.red_ball)
            Priority.MIDDLE -> binding.topPriority.setImageResource(R.drawable.yellow_ball)
            Priority.LOW -> binding.topPriority.setImageResource(R.drawable.green)
        }
    }

    /** 初始化recyclerView */
    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),RecyclerView.VERTICAL,false)
        binding.recyclerView.adapter = mAdapter

        //添加滑动删除
        swipeToDelete()
    }

    /** 滑动删除 */
    private fun swipeToDelete(){
        val touchHelper = ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //获取滑动的位置
                val index = viewHolder.adapterPosition
                //将数据从数据源里面删除
                val data = mainViewModel.todoDataList.value?.get(index+1)
                data?.let {
                    mainViewModel.deleteTodoData(it)
                    //提示删除完毕
                    showToast(requireContext(),"Delete Finished!")
                }
            }
        })
        touchHelper.attachToRecyclerView(binding.recyclerView)
    }

    /** 设置是否显示没数据的控件 */
    private fun checkEmptyStatus(dataList:List<Todo>){
        if (dataList.isEmpty()){
            binding.ivEmptyFace.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
            //隐藏头部视图
            binding.topTaskContainer.visibility = View.INVISIBLE
            //隐藏删除按钮
            binding.deleteAllBtn.visibility = View.INVISIBLE
        }else{
            binding.ivEmptyFace.visibility = View.INVISIBLE
            binding.tvEmpty.visibility = View.INVISIBLE
            //显示头部视图
            binding.topTaskContainer.visibility = View.VISIBLE
            //显示删除按钮
            binding.deleteAllBtn.visibility = View.VISIBLE
        }
    }
}