package com.example.vishingguard.login

import androidx.fragment.app.Fragment
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentSelectBinding

class SelectFragment : BindingFragment<FragmentSelectBinding>(R.layout.fragment_select) {
    override fun initView() {
        // 회원 가입으로 이동
        binding.btnEmail.setOnClickListener {
            replaceFragment(SignUpFragment())
        }

        // 로그인으로 이동
        binding.btnLogin.setOnClickListener {
            replaceFragment(SignInFragment())
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