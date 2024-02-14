package com.example.vishingguard.home.report.data

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vishingguard.R
import com.example.vishingguard.databinding.ItemReportBinding

class ReportAdapter(private val centerList: List<Report>) : RecyclerView.Adapter<ReportAdapter.GetCenterViewHolder>() {

    inner class  GetCenterViewHolder(val centerBinding: ItemReportBinding) : RecyclerView.ViewHolder(centerBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCenterViewHolder {
        val view = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetCenterViewHolder(view)
    }

    override fun onBindViewHolder(holder: GetCenterViewHolder, position: Int) {
        val currentItem = centerList[position]

        holder.centerBinding.tvNameCenter.text = currentItem.place
        holder.centerBinding.tvPhone.text = currentItem.number

        // initiate a phone call
        holder.centerBinding.btnCall.setOnClickListener {
            val phoneNumber = currentItem.number
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            holder.itemView.context.startActivity(intent)
        }

        // List of images to be displayed
        val imgList: ArrayList<Any> = arrayListOf()
        imgList.add(R.drawable.img_center_police)
        imgList.add(R.drawable.img_center_report)
        imgList.add(R.drawable.img_center_fss)
        imgList.add(R.drawable.img_center_nh)
        imgList.add(R.drawable.img_center_woori)
        imgList.add(R.drawable.img_center_hana)
        imgList.add(R.drawable.img_center_kookmin)
        imgList.add(R.drawable.img_center_ibk)
        imgList.add(R.drawable.img_center_shinhan)
        imgList.add(R.drawable.img_center_sc)
        imgList.add(R.drawable.img_center_suhyup)

        // Load and set image using Glide
        Glide.with(holder.centerBinding.imgProfile)
            .load(imgList[position])
            .into(holder.centerBinding.imgProfile)
    }

    override fun getItemCount(): Int {
        return centerList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}