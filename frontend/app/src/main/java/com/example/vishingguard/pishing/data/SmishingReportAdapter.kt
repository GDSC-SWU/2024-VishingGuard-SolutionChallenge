package com.example.vishingguard.pishing.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.databinding.ItemSmishingBinding

class SmishingReportAdapter(private val smishingReports: List<SmishingReport>) : RecyclerView.Adapter<SmishingReportAdapter.SmishingReportViewHolder>() {

    inner class SmishingReportViewHolder(val smishingBinding: ItemSmishingBinding) : RecyclerView.ViewHolder(smishingBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmishingReportViewHolder {
        val view = ItemSmishingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmishingReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmishingReportViewHolder, position: Int) {
        val currentItem = smishingReports[position]
        holder.smishingBinding.tvSmishingScript.text = currentItem.smishingScript
        holder.smishingBinding.tvPhone.text = currentItem.phone
        holder.smishingBinding.tvTime.text = currentItem.date +" "+ currentItem.time
        holder.smishingBinding.tvComment.text = currentItem.keywordComment
    }

    override fun getItemCount(): Int {
        return smishingReports.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}