package com.example.vishingguard.home.report

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentReportBinding
import com.example.vishingguard.home.report.data.ReportAdapter
import com.example.vishingguard.home.report.data.ReportViewModel

@Suppress("DEPRECATION")
class ReportFragment : BindingFragment<FragmentReportBinding>(R.layout.fragment_report) {

    private val viewModel by viewModels<ReportViewModel>()

    override fun initView() {
        // Request report data from the view model
        viewModel.getReport()

        // Handle the response from ViewModel
        handleCenterResponse()

        // Navigate to the main activity
        binding.btnBack.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun handleCenterResponse() {
        // Observe Report data
        viewModel.getReport.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                val data = response.data
                val adapter = ReportAdapter(data)
                binding.rvReportCenter.adapter = adapter
            }
        }
    }

    // Navigate to the main activity
    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }
}