package com.example.vishingguard.community.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.community.PostData
import com.example.vishingguard.databinding.ItemCommunityBinding

class PostsAdapter(private val postList: List<Post>, private val itemClickListener: OnPostItemClickListener) : RecyclerView.Adapter<PostsAdapter.PostListViewHolder>() {

    interface OnPostItemClickListener {
        fun onPostItemClick(postId: String){
            PostData.setPostId(postId)
        }
    }

    inner class  PostListViewHolder(val postBinding: ItemCommunityBinding) : RecyclerView.ViewHolder(postBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val view = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        val currentItem = postList[position]

        holder.postBinding.tvUsername.text = currentItem.username
        holder.postBinding.tvTitle.text = currentItem.title
        holder.postBinding.tvContent.text = currentItem.content
        holder.postBinding.tvTime.text = currentItem.createdAt
        holder.postBinding.tvHeart.text = currentItem.heartCount.toString()
        holder.postBinding.tvComment.text = currentItem.commentCount.toString()

        if(currentItem.updatedAt != null){
            holder.postBinding.tvTime.text = currentItem.updatedAt
        }

        holder.postBinding.root.setOnClickListener {
            itemClickListener.onPostItemClick(currentItem.postId)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}