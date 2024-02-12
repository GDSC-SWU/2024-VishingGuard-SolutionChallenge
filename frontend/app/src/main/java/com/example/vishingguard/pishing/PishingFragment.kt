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
        viewModel.getState()

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

        // Observe State data
        viewModel.getState.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                setPhishingResult(response.data)
            }
        }
    }

    // Show views indicating no data available
    private fun showNoDataViews(bgView: View, imageView: View, textView: View) {
        bgView.isVisible = true
        imageView.isVisible = true
        textView.isVisible = true
    }

    // Set phishing result based on the response data
    private fun setPhishingResult(state: String) {

        when (state) {
            "Risky" -> {
                setResultUI(
                    R.string.tv_title_risky, R.string.tv_result_risky, R.string.tv_content_risky,
                    R.drawable.ic_step_risky, R.drawable.ic_frame_risky, R.drawable.img_result_risky
                )
            }
            "Moderate" -> {
                setResultUI(
                    R.string.tv_title_moderate, R.string.tv_result_moderate, R.string.tv_content_moderate,
                    R.drawable.ic_step_moderate, R.drawable.ic_frame_moderate, R.drawable.img_result_moderate
                )
            }
            "Safe" -> {
                setResultUI(
                    R.string.tv_title_safe, R.string.tv_result_safe, R.string.tv_content_safe,
                    R.drawable.ic_step_safe, R.drawable.ic_frame_safe,R.drawable.img_result_safe
                )
            }
        }
    }

    // Set phishing result UI components
    private fun setResultUI(titleResId: Int, resultResId: Int, contentResId: Int, stepResId: Int, frameResId: Int, resultImageResId: Int) {
        binding.tvTitleResult.text = context?.getString(titleResId)
        binding.tvResult.text = context?.getString(resultResId)
        binding.tvResultContent.text = context?.getString(contentResId)
        binding.imgStepSafe.setImageResource(stepResId)
        binding.imgFrameSafe.setImageResource(frameResId)
        binding.imgResult.setImageResource(resultImageResId)
    }
}