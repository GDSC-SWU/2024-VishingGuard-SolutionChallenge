package com.example.vishingguard.community.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.community.PostData
import com.example.vishingguard.community.comment.create.CommentRequest
import com.example.vishingguard.databinding.CommentUpdateDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateCommentDialog : BottomSheetDialogFragment() {

    private var _binding: CommentUpdateDialogBinding? = null
    private val binding get() = _binding!!
    private val updateViewModel by viewModels<UpdateCommentViewModel>()
    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommentUpdateDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        // set text
        binding.etContent.setText(PostData.getComment())

        binding.btnDone.setOnClickListener {
            var content = binding.etContent.text.toString()
            val createRequest = CommentRequest(content = content)
            updateViewModel.patchComment(createRequest)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}