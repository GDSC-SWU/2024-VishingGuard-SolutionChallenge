package com.example.vishingguard.home.prevention.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.databinding.ItemPreventionBinding

class PreventionAdapter(private val preventionList: List<Prevention>) : RecyclerView.Adapter<PreventionAdapter.GetCenterViewHolder>() {

    inner class  GetCenterViewHolder(val preventionBinding: ItemPreventionBinding) : RecyclerView.ViewHolder(preventionBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCenterViewHolder {
        val view = ItemPreventionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetCenterViewHolder(view)
    }

    override fun onBindViewHolder(holder: GetCenterViewHolder, position: Int) {
        val currentItem = preventionList[position]

        holder.preventionBinding.tvTitlePrevention.text = currentItem.title
        holder.preventionBinding.tvContentPrevention.text = currentItem.content

        val num = position + 1
        holder.preventionBinding.tvNumPrevention.text = num.toString()
    }

    override fun getItemCount(): Int {
        return preventionList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}