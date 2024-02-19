package com.example.vishingguard.home.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vishingguard.databinding.ItemBannerBinding

class BannerAdapter(private val imageList: List<Any>, private val onBannerClickListener: OnBannerClickListener) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    // Define listener interface to handle click events
    interface OnBannerClickListener {
        fun onBannerClick(position: Int)
    }

    inner class BannerViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define method to be called when each banner is clicked
            binding.root.setOnClickListener {
                val position = adapterPosition // Get the position of the clicked banner
                if (position != RecyclerView.NO_POSITION) {
                    // Pass the position of the clicked banner to the click listener
                    onBannerClickListener.onBannerClick(position)
                }
            }
        }

        fun bind(item: Any) {
            // Use Glide to load and set the image
            Glide.with(binding.imgBanner)
                .load(item)
                .into(binding.imgBanner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // Inflate the item layout and create a new ViewHolder
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // Bind data to each item
        val currentItem = imageList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        // Return the total number of items
        return imageList.size
    }
}