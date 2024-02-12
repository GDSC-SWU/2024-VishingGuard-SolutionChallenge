package com.example.vishingguard.pishing

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentPishingBinding
import com.example.vishingguard.pishing.data.ReportViewModel
import com.example.vishingguard.pishing.data.SmishingReportAdapter
import com.example.vishingguard.pishing.data.VishingReportAdapter

class PishingFragment : BindingFragment<FragmentPishingBinding>(R.layout.fragment_pishing) {

    private val viewModel by viewModels<ReportViewModel>()
    override fun initView() {
        // Request to post reports to the server
        viewModel.postVishing()
        viewModel.postSmishing()

        // Handle the response from ViewModel
        handleReportResponse()
    }

    private fun handleReportResponse() {
        // Observe Vishing data
        viewModel.postVishing.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                // populate RecyclerView with Vishing reports
                val data = response.data
                val adapter = VishingReportAdapter(data)
                binding.rvVishing.adapter = adapter
            } else {
                // No Vishing Data views
                showNoDataViews(binding.bgCall, binding.imgCall, binding.tvResultCall)
            }
        }

        // Observe Smishing data
        viewModel.postSmishing.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                // populate RecyclerView with Smishing reports
                val data = response.data
                val adapter = SmishingReportAdapter(data)
                binding.rvSmishing.adapter = adapter
            } else {
                // No Smishing Data views
                showNoDataViews(binding.bgMessage, binding.imgMessage, binding.tvResultMassage)
            }
        }
    }

    // Show views indicating no data available
    private fun showNoDataViews(bgView: View, imageView: View, textView: View) {
        bgView.isVisible = true
        imageView.isVisible = true
        textView.isVisible = true
    }
}