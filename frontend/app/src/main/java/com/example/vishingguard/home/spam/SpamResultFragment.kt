package com.example.vishingguard.home.spam

import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentSearchResultBinding
import com.example.vishingguard.home.spam.data.SpamCheckRequest
import com.example.vishingguard.home.spam.data.SpamCheckViewModel

@Suppress("DEPRECATION")
class SpamResultFragment : BindingFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val viewModel by viewModels<SpamCheckViewModel>()

    override fun initView() {
        // Post the spam number for check
        postSpamNumber()

        binding.btnDone.setOnClickListener {
            navigateToMainActivity()
        }

        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    // Post the spam number to the server for check
    private fun postSpamNumber() {
        val phoneNum = arguments?.getString("phoneNum")
        binding.tvPhone.text = phoneNum

        // If the phone number exists
        if (phoneNum != null) {
            val spamCheck = SpamCheckRequest(phoneNum)
            viewModel.postSpamCheck(spamCheck)
        }

        // Observe SpamCheck data
        viewModel.postSpamCheck.observe(viewLifecycleOwner) { response ->

            binding.icSearch.visibility = View.VISIBLE
            binding.tvSearchCount.text = getString(R.string.tv_search_count) + " ${response.data.count} times."

            // If the number is spam
            if (response.data.result) {
                binding.tvResultSearch.text = getString(R.string.tv_result_search) + " ${response.data.name}"
                binding.bgSearch.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red))
                binding.imgResultSearch.setImageResource(R.drawable.img_search_risky)
            }
            else{
                binding.tvResultSearch.text = getString(R.string.tv_result_search) + " Safe"
                binding.bgSearch.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                binding.imgResultSearch.setImageResource(R.drawable.img_search_safe)
            }
        }
    }

    // Navigate back
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    // Navigate to the main activity
    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }
}