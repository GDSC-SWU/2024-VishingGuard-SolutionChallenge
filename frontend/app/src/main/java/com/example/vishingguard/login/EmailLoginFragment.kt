package com.example.vishingguard.login

import android.content.Intent
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentEmailLoginBinding

class EmailLoginFragment : BindingFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login) {
    override fun initView() {
        // 뒤로가기
        binding.btnBack.setOnClickListener {
            navigateBack()
        }

        // 메인 화면으로 이동
        binding.btnLogin.setOnClickListener {
            navigateToMainActivity()
        }
    }

    // 뒤로가기
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    // 화면 이동
    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }
}