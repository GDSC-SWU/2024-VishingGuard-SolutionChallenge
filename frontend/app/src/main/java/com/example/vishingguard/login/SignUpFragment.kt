package com.example.vishingguard.login

import androidx.fragment.app.Fragment
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentSignUpBinding

class SignUpFragment : BindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {
    override fun initView() {

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            navigateBack()
        }

        // 이메일 로그인으로 이동
        binding.btnNext.setOnClickListener {
            replaceFragment(EmailLoginFragment())
        }
    }
    // 뒤로가기
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }
    // 프래그먼트 이동
    fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_login, fragment)
            .addToBackStack(null)
            .commit()
    }
}