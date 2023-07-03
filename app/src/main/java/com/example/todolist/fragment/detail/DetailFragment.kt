package com.example.todolist.fragment.detail

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.dp2px
import com.example.todolist.fragment.main.MainViewModel
import com.example.todolist.R
import com.example.todolist.databinding.FragmentDetailBinding
import com.example.todolist.db.model.Date
import com.example.todolist.db.model.Priority
import com.example.todolist.db.model.Tag
import com.example.todolist.db.model.TagData
import com.example.todolist.db.model.Todo
import java.util.*


class DetailFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    //接收传递的参数
    private val mCurrentTodoArgs:DetailFragmentArgs by navArgs()
    //将args解析出来
    private var mCurrentTodo:Todo? = null// = mCurrentTodoArgs.currentTodo
    private lateinit var binding: FragmentDetailBinding
    private var mPriority = Priority.LOW
    private var mTagData: TagData? = null
    private var lastSelectedTagView:View? = null
    private var mDate = Date(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCurrentTodo = mCurrentTodoArgs.currentTodo
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //优先级
        initPriorityEvent()
        //日期
        initDateEvent()
        //菜单按钮
        initMenuEvent()
        //返回按钮
        binding.backBtn.setOnClickListener { goback() }
        //初始化显示的数据
        initData()
        //save还是update
        if (mCurrentTodo != null){
            binding.saveBtn.text = "Update"
        }else{
            binding.deleteBtn.visibility = View.INVISIBLE
        }
        //addtag事件
        initAddTagEvent()
        //观察tag标签属性
        detailViewModel.tagList.observe(viewLifecycleOwner) {
            //将容器之前的子视图清除
            binding.tagContainer.removeAllViews()

            //改变tag的显示
            it.forEach { tagData ->
                //创建一个TextView显示tag
                TextView(requireContext()).apply {
                    text = tagData.title
                    setBackgroundColor(Color.parseColor(tagData.bgColor))
                    setTextColor(Color.WHITE)
                    setPadding(
                        dp2px(requireContext(),15),
                        dp2px(requireContext(),10),
                        dp2px(requireContext(),15),
                        dp2px(requireContext(),10),
                    )
                    setOnClickListener {
                        if (lastSelectedTagView != null) {
                            lastSelectedTagView?.setBackgroundColor(Color.parseColor(mTagData?.bgColor))
                        }
                        mTagData = tagData
                        lastSelectedTagView = this
                        this.setBackgroundColor(requireActivity().getColor(R.color.main_purple))
                    }
                    binding.tagContainer.addView(this)
                }
            }
        }
    }
    private fun initAddTagEvent(){
        binding.addTagBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_addTagDialogFragment)
        }
    }
    private fun initData(){
        mCurrentTodo?.let {
            //需要将数据显示到界面上
            binding.titleEditText.setText(it.title)
            setPriority(it.priority)
            mDate.year = it.date.year
            mDate.month = it.date.month
            mDate.day = it.date.day
            binding.dateBtn.text = "${it.date.year}-${it.date.month+1}-${it.date.day}"
            binding.descriptionTextView.setText(it.description)
        }
    }
    private fun setPriority(priority: Priority){
        mPriority = priority

        binding.priorityhightBtn
            .setTextColor(requireContext().getColor(R.color.white))
        binding.priorityMiddleBtn
            .setTextColor(requireContext().getColor(R.color.white))
        binding.priorityLowBtn
            .setTextColor(requireContext().getColor(R.color.white))
        when(priority){
            Priority.HIGH->binding.priorityhightBtn
                .setTextColor(requireContext().getColor(R.color.main_purple))
            Priority.MIDDLE->binding.priorityMiddleBtn
                .setTextColor(requireContext().getColor(R.color.main_purple))
            Priority.LOW->binding.priorityLowBtn
                .setTextColor(requireContext().getColor(R.color.main_purple))
        }
    }
    private fun goback(){
        requireActivity().onBackPressed()
        //findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
    }
    private fun initMenuEvent(){
        binding.menuBtn.setOnClickListener {
            binding.optionBtnContainer.visibility = View.VISIBLE
            binding.optionBtnContainer.alpha = 1f
            ObjectAnimator.ofFloat(
                binding.optionBtnContainer,"rotationX",-90f,0f).apply {
                duration = 300
                interpolator = BounceInterpolator()
                start()
            }
        }
        binding.saveBtn.setOnClickListener {
            if (checkInputValid()){
                if (mCurrentTodo == null) {
                    //创建Todo对象
                    val todoData = Todo(
                        0,
                        binding.titleEditText.text.toString(),
                        binding.descriptionTextView.text.toString(),
                        mPriority,
                        mDate,
                        Tag(mTagData!!.title, mTagData!!.bgColor)
                    )
                    //插入数据
                    mainViewModel.insertTodoData(todoData)
                }else{
                    mCurrentTodo?.title = binding.titleEditText.text.toString()
                    mCurrentTodo?.description = binding.descriptionTextView.text.toString()
                    mCurrentTodo?.priority = mPriority
                    mCurrentTodo?.date = mDate
                    if (mTagData != null) {
                        mCurrentTodo?.tag = Tag(mTagData!!.title, mTagData!!.bgColor)
                    }
                    //更新数据
                    mainViewModel.updateTodoData(mCurrentTodo!!)
                }
                //返回上一个页面
                goback()
            }else{
                Toast.makeText(
                    requireContext(),
                    "Todo数据不能为空",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.deleteBtn.setOnClickListener {
            mainViewModel.deleteTodoData(mCurrentTodo!!)
            goback()
        }
    }
    private fun checkInputValid():Boolean{
        return binding.titleEditText.text.isNotEmpty()&&
                binding.descriptionTextView.text.isNotEmpty()&&
                mTagData != null
    }
    private fun optionBtnHideAnim(){
        binding.optionBtnContainer
            .animate()
            .alpha(0f)
            .rotationX(90f)
            .setDuration(300)
            .setListener(object:Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    binding.optionBtnContainer.visibility = View.GONE
                }
                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
    }
    private fun initPriorityEvent(){
        //优先级按钮点击事件
        binding.priorityhightBtn.setOnClickListener{
            setPriority(Priority.HIGH)
        }
        binding.priorityMiddleBtn.setOnClickListener{
            setPriority(Priority.MIDDLE)
        }
        binding.priorityLowBtn.setOnClickListener{
            setPriority(Priority.LOW)
        }
    }
    private fun initDateEvent(){
        //日期的点击事件
        binding.dateBtn.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                object:DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        mDate.year = year
                        mDate.month = month
                        mDate.day = dayOfMonth

                        //将日期显示到textView上
                        binding.dateBtn.text = "$year-${month+1}-$dayOfMonth"
                    }
                },
                mDate.year,mDate.month,mDate.day
            ).show()
        }
    }
}