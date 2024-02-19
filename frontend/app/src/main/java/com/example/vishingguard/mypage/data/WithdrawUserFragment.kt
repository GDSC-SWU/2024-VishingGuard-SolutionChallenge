package com.example.vishingguard.mypage.data

import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentWithdrawUserBinding

class WithdrawUserFragment : BindingFragment<FragmentWithdrawUserBinding>(R.layout.fragment_withdraw_user) {

    override fun initView() {
        // Navigate back
        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Call updateUser method
        binding.btnDone.setOnClickListener {
            showDialog()
        }
    }

    // Send update user information request to the server
    private fun showDialog() {
        val password = binding.inputPw.editText?.text.toString()
        val withdrawUserDialog = WithdrawUserDialog(requireContext(), password)
        withdrawUserDialog.show()
    }
}