package com.example.vishingguard.community.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.databinding.CommentDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    private var _binding: CommentDialogBinding? = null
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
        _binding = CommentDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvEdit.setOnClickListener {
            val dialogFragment = UpdateCommentDialog()
            fragmentManager?.let { it1 -> dialogFragment.show(it1, "Update CommentDialog") }
            dismiss()
        }

        binding.tvDelete.setOnClickListener {
            updateViewModel.deleteComment()
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}