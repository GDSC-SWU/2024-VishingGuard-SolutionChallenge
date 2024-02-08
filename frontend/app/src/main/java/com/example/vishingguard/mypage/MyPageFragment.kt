package com.example.vishingguard.mypage

import androidx.fragment.app.Fragment
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentMyPageBinding

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {

        // 정보 수정 화면으로 이동
        binding.btnUpdate.setOnClickListener {
            replaceFragment(UpdateFragment())
        }
    }

    // 프래그먼트 이동
    fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}