package com.example.vishingguard.mypage

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentUpdateUserBinding
import com.example.vishingguard.mypage.data.MyPageViewModel
import com.example.vishingguard.mypage.data.UpdateUserRequest

class UpdateFragment : BindingFragment<FragmentUpdateUserBinding>(R.layout.fragment_update_user) {

    private val viewModel by viewModels<MyPageViewModel>()

    override fun initView() {
        // Navigate back
        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Call updateUser method
        binding.btnDone.setOnClickListener {
            updateUser()
        }
    }

    // Send update user information request to the server
    private fun updateUser() {
        val username = binding.inputName.editText?.text.toString()
        val password = binding.inputCheckPw.editText?.text.toString()

        val updateUserRequest = UpdateUserRequest(username = username, password = password)
        viewModel.updateUser(updateUserRequest)

        // Handle response after calling updateUser method
        handleUpdateResponse()
    }

    // Handle response after updateUser method call
    private fun handleUpdateResponse() {
        viewModel.updateUser.observe(viewLifecycleOwner) { response ->
            if (response.status == 200) {
                Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
        }
    }
}