package com.example.vishingguard.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentEmailLoginBinding
import com.example.vishingguard.login.data.LoginRequest
import com.example.vishingguard.login.data.LoginViewModel

class EmailLoginFragment : BindingFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login) {

    private val viewModel by viewModels<LoginViewModel>()

    companion object {
        const val STATUS_SUCCESS = 200
    }

    override fun initView() {
        // Login button click listener
        binding.btnLogin.setOnClickListener {
            postLogin()
        }

        // Back button click listener
        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    // Connect to server for login
    private fun postLogin() {
        val email = binding.inputEmail.editText?.text.toString()
        val pw = binding.inputPw.editText?.text.toString()

        val loginRequest = LoginRequest(email = email, password = pw)
        viewModel.postLogin(loginRequest)

        handleLoginResponse()
    }

    private fun handleLoginResponse() {
        // If login is successful
        if (viewModel.postLogin.value?.status == STATUS_SUCCESS) {
            binding.inputPw.error = null
            navigateToMainActivity() // Navigate to main activity

            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
        }

        // If login fails
        else {
            // Set error message
            val failureMessage = viewModel.failureMessage.value.toString()
            val userNotFoundMessage = extractErrorMessage(failureMessage)
            binding.inputPw.error = userNotFoundMessage
            Log.d("error", "error : ${userNotFoundMessage}")
        }
    }

    // Extract message content from error message
    private fun extractErrorMessage(response: String): String {
        val pattern = Regex("""\"message\":\"(.*?)\"""")
        val matchResult = pattern.find(response)
        return matchResult?.groupValues?.get(1) ?: ""
    }

    // Navigate back
    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    // Navigate to main activity
    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }
}
