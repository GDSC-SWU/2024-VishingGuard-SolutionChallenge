package com.example.vishingguard.community

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.community.create.CreateRequest
import com.example.vishingguard.community.create.CreateViewModel
import com.example.vishingguard.databinding.FragmentCreatePostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreatePostFragment : BindingFragment<FragmentCreatePostBinding>(R.layout.fragment_create_post) {

    private val viewModel by viewModels<CreateViewModel>()

    override fun initView() {
        // Move to the phishing report screen
        binding.btnBack.setOnClickListener {
            navigateToMainActivity()
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
        viewModel.postCreate(createRequest)

        replaceFragment(CommunityFragment())
    }

    // Navigate to the main activity
    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }

    // Replace fragments
    fun replaceFragment(fragment: Fragment){
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}