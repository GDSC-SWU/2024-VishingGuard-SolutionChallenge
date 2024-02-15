package com.example.vishingguard.home

import androidx.viewpager2.widget.ViewPager2
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentHomeBinding
import com.example.vishingguard.home.data.BannerAdapter

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initView() {
        // Create a list of images for the banner
        val imgList: ArrayList<Any> = arrayListOf()
        imgList.add(R.drawable.img_banner1)
        imgList.add(R.drawable.img_banner2)
        imgList.add(R.drawable.img_banner3)

        // Set up ViewPager for the banner
        binding.viewPagerBanner.offscreenPageLimit = 1 // Load one page in advance
        binding.viewPagerBanner.adapter = BannerAdapter(imgList)
        binding.viewPagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Set up dots indicator for the banner
        binding.dotsIndicator.setViewPager2(binding.viewPagerBanner)
    }
}