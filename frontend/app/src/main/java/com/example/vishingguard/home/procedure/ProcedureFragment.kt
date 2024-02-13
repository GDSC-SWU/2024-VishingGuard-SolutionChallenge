package com.example.vishingguard.home.procedure

import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentProcedureBinding
import com.example.vishingguard.home.procedure.data.ProcedureAdapter
import com.example.vishingguard.home.procedure.data.ProcedureViewModel

class ProcedureFragment : BindingFragment<FragmentProcedureBinding>(R.layout.fragment_procedure) {

    private val viewModel by viewModels<ProcedureViewModel>()

    override fun initView() {

        viewModel.getProcedure()

        handleCenterResponse()

        // Navigate back
        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun handleCenterResponse() {
        // Observe ReportCenter data
        viewModel.getProcedure.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                val data = response.data
                val adapter = ProcedureAdapter(data)
                binding.rvProcedure.adapter = adapter
            }
        }
    }

    // Navigate back
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }
}