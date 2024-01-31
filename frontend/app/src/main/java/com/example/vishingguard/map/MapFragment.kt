package com.example.vishingguard.map

import android.view.View
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentMapBinding

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {
    override fun initView() {

        // 지도 숨기기
        val mapFragment = view?.findViewById<View>(R.id.map_road)
        mapFragment?.visibility = View.GONE
    }
}