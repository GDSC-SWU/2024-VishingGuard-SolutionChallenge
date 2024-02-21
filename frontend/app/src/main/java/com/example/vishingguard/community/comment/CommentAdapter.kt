package com.example.vishingguard.community.comment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.community.PostData
import com.example.vishingguard.databinding.ItemCommentBinding
import com.example.vishingguard.login.data.UserData

class CommentAdapter(private val commentList: List<Comment>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<CommentAdapter.CommentListViewHolder>() {

    // ViewHolder for the comment item
    inner class  CommentListViewHolder(val commentBinding: ItemCommentBinding) : RecyclerView.ViewHolder(commentBinding.root)

    // Inflates the layout for each comment item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentListViewHolder(view)
    }

    // Binds data to the views in each comment item
    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        val currentItem = commentList[position]

        // Set username and comment content
        holder.commentBinding.tvUsername.text = currentItem.username
        holder.commentBinding.tvComment.text = currentItem.content

        // Set time if available
        if(currentItem.updatedAt != null){
            holder.commentBinding.tvTime.text = currentItem.updatedAt
        }

        // Show menu button if the comment belongs to the current user
        if(UserData.getUserId() == currentItem.userId){
            holder.commentBinding.btnMenu.visibility = View.VISIBLE
        }

        // Handle click on the menu button
        holder.commentBinding.btnMenu.setOnClickListener {
            // Set the selected comment data
            PostData.setComment(currentItem.content)
            PostData.setCommentId(currentItem.commentId)
            Log.d("currentItem","currentItem.content : ${currentItem.content}")
            Log.d("currentItem","currentItem.commentId : ${currentItem.commentId}")

            // Show the comment dialog
            val dialogFragment = CommentDialog()
            dialogFragment.show(fragmentManager, "CommentDialog")
        }
    }

    // Returns the total number of comments
    override fun getItemCount(): Int {
        return commentList.size
    }
}