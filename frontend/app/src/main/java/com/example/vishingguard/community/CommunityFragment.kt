package com.example.vishingguard.community

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.community.posts.PostsAdapter
import com.example.vishingguard.community.posts.PostsViewModel
import com.example.vishingguard.databinding.FragmentCommunityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CommunityFragment : BindingFragment<FragmentCommunityBinding>(R.layout.fragment_community), PostsAdapter.OnPostItemClickListener {

    private val viewModel by viewModels<PostsViewModel>()
    private lateinit var adapter: PostsAdapter

    override fun initView() {
        viewModel.getPosts()

        adapter = PostsAdapter(emptyList(), this@CommunityFragment)
        binding.rvCommunity.adapter = adapter

        handlePostsResponse()

        // Navigate back to MainActivity when the back button is clicked
        binding.btnBack.setOnClickListener {
            navigateToMainActivity()
        }

        // Open CreatePostFragment when the FAB (Floating Action Button) is clicked
        binding.fabWrite.setOnClickListener{
            replaceFragment(CreatePostFragment())
        }
    }

    private fun handlePostsResponse() {
        // Observe the posts response and update the RecyclerView accordingly
        viewModel.getPosts.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.data.isNotEmpty()) {
                    val data = it.data
                    val adapter = PostsAdapter(data, this@CommunityFragment)
                    binding.rvCommunity.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        // Navigate back to MainActivity and reset the transition animation
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }

    override fun onPostItemClick(postId: String) {
        // Open ReadFragment when a post item is clicked and pass the postId to the fragment
        val fragment = ReadFragment()
        PostData.setPostId(postId)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment){
        // Replace the current fragment with the new one and hide the bottom navigation view
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}