package com.example.vishingguard.mypage

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentMyPageBinding

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {

        // 정보 수정 화면으로 이동
        binding.btnEdit.setOnClickListener {
            navigateToEditFragment()
        }
    }

    // EditFragment로 이동
    private fun navigateToEditFragment() {
        val editFragment = EditFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.container_main, editFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}