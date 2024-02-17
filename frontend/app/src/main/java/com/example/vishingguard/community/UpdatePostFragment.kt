package com.example.vishingguard.community

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.community.create.CreateRequest
import com.example.vishingguard.community.update.UpdateViewModel
import com.example.vishingguard.databinding.FragmentUpdatePostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class UpdatePostFragment : BindingFragment<FragmentUpdatePostBinding>(R.layout.fragment_update_post) {

    private val viewModel by viewModels<UpdateViewModel>()

    override fun initView() {
        // set text
        binding.etTitle.setText(PostData.getTitle())
        binding.etContent.setText(PostData.getContent())

        binding.btnBack.setOnClickListener {
            navigateBack()
        }

        binding.tvDone.setOnClickListener {
            // Request to fetch posts from the server
            postCreate()
        }
    }

    private fun postCreate() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()

        val createRequest = CreateRequest(title = title, content = content)
        viewModel.updatePost(createRequest)

        replaceFragment(CommunityFragment())
    }

    // 뒤로가기
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }


    // Replace fragments
    fun replaceFragment(fragment: Fragment){
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
    }
}