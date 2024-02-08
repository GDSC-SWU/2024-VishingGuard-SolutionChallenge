package com.example.vishingguard.mypage

import androidx.navigation.fragment.findNavController
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentUpdateBinding

class UpdateFragment : BindingFragment<FragmentUpdateBinding>(R.layout.fragment_update) {
    override fun initView() {
        // 뒤로가기
        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // 마이 페이지로 이동
        binding.btnDone.setOnClickListener {
            navigateToMyPage()
        }
    }

    // 마이 페이지로 이동
    private fun navigateToMyPage() {
        val navController = findNavController()
        navController.navigate(R.id.navigation_my_page) // myPageFragment의 ID를 사용하여 이동
    }
}