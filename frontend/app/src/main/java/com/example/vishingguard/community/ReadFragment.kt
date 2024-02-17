package com.example.vishingguard.community

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.community.comment.CommentAdapter
import com.example.vishingguard.community.comment.CommentViewModel
import com.example.vishingguard.community.comment.UpdateCommentViewModel
import com.example.vishingguard.community.comment.create.CommentRequest
import com.example.vishingguard.community.read.ReadViewModel
import com.example.vishingguard.databinding.FragmentReadBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReadFragment : BindingFragment<FragmentReadBinding>(R.layout.fragment_read) {
    private lateinit var commentIdList: Array<String>
    private val readViewModel by viewModels<ReadViewModel>()
    private val commentViewModel by viewModels<CommentViewModel>()
    private val updateViewModel by viewModels<UpdateCommentViewModel>()
    private lateinit var commentAdapter: CommentAdapter

    override fun initView() {
        // Request to fetch posts from the server
        readViewModel.getRead()
        commentViewModel.getComment()
        // Handle the response from ViewModel
        handlePostsResponse()
        observeCommentUpdates()

        binding.btnSend.setOnClickListener {
            val content = binding.etComment.text.toString()
            val createRequest = CommentRequest(content = content)
            binding.etComment.text = null
            commentViewModel.postComment(createRequest)
        }

        binding.btnMenu.setOnClickListener {
            val dialogFragment = PostDialog()
            dialogFragment.show(childFragmentManager, "PostDialog")
        }

        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    // Handles responses for post retrieval and comment updates
    private fun handlePostsResponse() {
        readViewModel.getRead.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                binding.tvUsername.text = data.username
                binding.tvTitle.text = data.title
                binding.tvContent.text = data.content
                binding.tvTime.text = data.createdAt
                binding.tvHeart.text = data.heartCount.toString()
                binding.tvComment.text = data.commentCount.toString()

                if(data.updatedAt != null){
                    binding.tvTime.text = data.updatedAt
                }

                // Shows menu button for own posts
                if(data.myPost){
                    binding.btnMenu.visibility = View.VISIBLE
                    PostData.setPostContent(data.title, data.content)
                }
            }
        }

        commentViewModel.getComment.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.data.isNotEmpty()) {
                    val data = it.data
                    commentAdapter = CommentAdapter(data, childFragmentManager) // Initialize the comment adapter
                    binding.rvComment.adapter = commentAdapter
                    // Apply RecyclerView when new comments are added
                    commentIdList = data.map { it.commentId }.toTypedArray()
                }
            }
        }
    }

    // Observes comment updates (update and delete)
    private fun observeCommentUpdates() {
        // Observer for comment update (edit)
        updateViewModel.patchComment.observe(viewLifecycleOwner) { updatedComment ->
            // Update the changed comment
            val position = commentIdList.indexOfFirst { it == updatedComment.data.commentId }
            if (position != -1) {
                commentAdapter.notifyItemChanged(position) // Use the comment adapter object to make the call
            }
            readViewModel.getRead()
        }

        // Observer for comment update (delete)
        updateViewModel.deleteComment.observe(viewLifecycleOwner) { deletedComment ->
            // Remove the deleted comment from the RecyclerView
            val position = commentIdList.indexOfFirst { it == deletedComment.data }
            if (position != -1) {
                commentIdList = commentIdList.toMutableList().apply { removeAt(position) }.toTypedArray()
                // Update the RecyclerView when a comment is deleted
                commentAdapter.notifyItemRangeChanged(position, commentIdList.size)
                readViewModel.getRead()
            }
        }
    }

    // Navigates back
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    // Replaces fragments
    fun replaceFragment(fragment: Fragment){
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}