package com.example.vishingguard.map.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vishingguard.databinding.ItemRouteBinding

class RouteAdapter(private val timeList: List<String>, private val arrivedList: List<String>) : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    inner class RouteViewHolder(val smishingBinding: ItemRouteBinding) : RecyclerView.ViewHolder(smishingBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = ItemRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        val currentItem = arrivedList[position]

        holder.smishingBinding.tvTime.text = timeList[position]
        holder.smishingBinding.tvPlace.text = currentItem

        if (position != 0) {
            holder.smishingBinding.imgArrow.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return arrivedList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}