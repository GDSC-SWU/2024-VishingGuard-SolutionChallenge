package com.example.vishingguard.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.community.delete.DeleteViewModel
import com.example.vishingguard.databinding.PostDialogBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostDialog : BottomSheetDialogFragment() {

    private var _binding: PostDialogBinding? = null
    private val binding get() = _binding!!
    private val deleteViewModel by viewModels<DeleteViewModel>()
    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvEdit.setOnClickListener {
            replaceFragment(UpdatePostFragment())
            dismiss()
        }

        binding.tvDelete.setOnClickListener {
            deleteViewModel.deletePost()
            replaceFragment(CommunityFragment())
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun replaceFragment(fragment: Fragment){
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}