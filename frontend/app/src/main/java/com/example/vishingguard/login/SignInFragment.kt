package com.example.vishingguard.login

import androidx.fragment.app.Fragment
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentSignInBinding

class SignInFragment : BindingFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    override fun initView() {

        // 이메일 로그인으로 이동
        binding.btnEmailLogin.setOnClickListener {
            replaceFragment(EmailLoginFragment())
        }

        // 구글 로그인
        binding.btnGoogleLogin.setOnClickListener {

        }
    }

    // 프래그먼트 이동
    fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_login, fragment)
            .addToBackStack(null)
            .commit()
    }
}