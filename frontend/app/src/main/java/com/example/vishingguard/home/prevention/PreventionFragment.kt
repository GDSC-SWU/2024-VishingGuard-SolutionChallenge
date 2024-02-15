package com.example.vishingguard.home.prevention

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentPreventionBinding
import com.example.vishingguard.home.prevention.data.PreventionAdapter
import com.example.vishingguard.home.prevention.data.PreventionViewModel

@Suppress("DEPRECATION")
class PreventionFragment : BindingFragment<FragmentPreventionBinding>(R.layout.fragment_prevention) {

    private val viewModel by viewModels<PreventionViewModel>()

    override fun initView() {

        viewModel.getPrevention()

        handleCenterResponse()

        binding.btnBack.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun handleCenterResponse() {
        // Observe ReportCenter data
        viewModel.getPrevention.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                val data = response.data
                val adapter = PreventionAdapter(data)
                binding.rvPrevention.adapter = adapter
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