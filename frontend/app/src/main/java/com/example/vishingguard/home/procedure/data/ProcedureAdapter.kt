package com.example.vishingguard.home.procedure.data

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.databinding.ItemProcedureBinding

class ProcedureAdapter(private val procedureList: List<Procedure>) : RecyclerView.Adapter<ProcedureAdapter.GetCenterViewHolder>() {

    inner class  GetCenterViewHolder(val procedureBinding: ItemProcedureBinding) : RecyclerView.ViewHolder(procedureBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCenterViewHolder {
        val view = ItemProcedureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetCenterViewHolder(view)
    }

    override fun onBindViewHolder(holder: GetCenterViewHolder, position: Int) {
        val currentItem = procedureList[position]

        holder.procedureBinding.tvTitleProcedure.text = currentItem.title
        holder.procedureBinding.tvContentProcedure.text = currentItem.content
        holder.procedureBinding.tvUrlProcedure.text = currentItem.url

        if (currentItem.url == null) {
            // Link hide
            holder.procedureBinding.tvUrlProcedure.visibility = View.GONE
        }
        else {
            // Open the link
            holder.procedureBinding.tvUrlProcedure.setOnClickListener {
                val url = currentItem.url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            }
        }
        // Display item number
        val num = position + 1
        holder.procedureBinding.tvNumProcedure.text = num.toString()
    }

    override fun getItemCount(): Int {
        return procedureList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}