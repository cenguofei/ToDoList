package com.example.todolist.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.todolist.databinding.AddTagDialogBinding
import com.example.todolist.db.model.TagData
import com.example.todolist.showToast

class AddTagDialogFragment: DialogFragment(){
    private lateinit var binding:AddTagDialogBinding
    private var bgColor = "#ff8b7c"
    private val mViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTagDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.color1.setOnClickListener { colorViewSelected(it) }
        binding.color2.setOnClickListener { colorViewSelected(it) }
        binding.color3.setOnClickListener { colorViewSelected(it) }
        binding.color4.setOnClickListener { colorViewSelected(it) }

        binding.cancelBtn.setOnClickListener { dismiss() }
        binding.okBtn.setOnClickListener { saveTag()}
    }

    private fun saveTag(){
        if (binding.tagNameEditText.text.isEmpty()){
            showToast(requireContext(),"标签名称不能为空")
        }else{
            mViewModel.insertTag(
                TagData(
                0,binding.tagNameEditText.text.toString(),bgColor)
            )
            dismiss()
        }
    }

    private fun colorViewSelected(view:View){
        binding.underline
            .animate()
            .translationX(view.x-binding.color1.x)
            .setDuration(500)
            .start()
        bgColor = view.tag as String
    }
}