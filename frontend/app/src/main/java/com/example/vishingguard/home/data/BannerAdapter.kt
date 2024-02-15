package com.example.vishingguard.home.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vishingguard.databinding.ItemBannerBinding

class BannerAdapter(private val imageList: List<Any>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    // ViewHolder for the banner images
    inner class BannerViewHolder(val imgBinding: ItemBannerBinding) : RecyclerView.ViewHolder(imgBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // Inflate the layout for each banner item
        val view = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // Bind data to each item
        val currentItem = imageList[position]

        // Load and set image using Glide
        Glide.with(holder.imgBinding.imgBanner)
            .load(currentItem)
            .into(holder.imgBinding.imgBanner)
    }

    override fun getItemCount(): Int {
        // Return the total number of items
        return imageList.size
    }
}