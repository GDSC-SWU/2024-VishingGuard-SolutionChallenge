package com.example.vishingguard.pishing.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.databinding.ItemVishingBinding

class VishingReportAdapter(private val vishingReports: List<VishingReport>) : RecyclerView.Adapter<VishingReportAdapter.VishingReportViewHolder>() {

    inner class  VishingReportViewHolder(val vishingBinding: ItemVishingBinding) : RecyclerView.ViewHolder(vishingBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VishingReportViewHolder {
        val view = ItemVishingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VishingReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: VishingReportViewHolder, position: Int) {
        val currentItem = vishingReports[position]

        holder.vishingBinding.tvPhone.text = currentItem.phone
        holder.vishingBinding.tvTime.text = currentItem.date +" "+ currentItem.time
        holder.vishingBinding.tvComment.text = currentItem.keywordComment
    }

    override fun getItemCount(): Int {
        return vishingReports.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}