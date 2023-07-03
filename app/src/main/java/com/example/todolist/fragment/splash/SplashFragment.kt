package com.example.todolist.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var timer:Timer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        //点击跳转按钮
        binding.jumbBtn.setOnClickListener {
            //取消定时器
            timer.cancel()
            //执行跳转
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment2)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        timer = Timer()
        timer.schedule(object:TimerTask() {
            override fun run() {
                lifecycleScope.launch(Dispatchers.Main){
                    //跳转到主页面
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment2)
                }
            }
        },3000)
    }
}