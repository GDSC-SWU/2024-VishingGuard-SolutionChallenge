package com.example.vishingguard.home.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.databinding.ItemPostBinding

class HomeAdapter(private val postList: List<Home>) : RecyclerView.Adapter<HomeAdapter.PostListViewHolder>() {

    inner class  PostListViewHolder(val homeBinding: ItemPostBinding) : RecyclerView.ViewHolder(homeBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val view = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        val currentItem = postList[position]
        holder.homeBinding.tvTitlePost.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}