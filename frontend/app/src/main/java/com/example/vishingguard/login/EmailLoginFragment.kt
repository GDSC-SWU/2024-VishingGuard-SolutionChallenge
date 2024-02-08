package com.example.vishingguard.login

import android.content.Intent
import android.util.Log
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
        // 로그인
        binding.btnLogin.setOnClickListener {
            postLogin()
        }

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    // 서버 연결
    private fun postLogin() {
        val email = binding.inputEmail.editText?.text.toString()
        val pw = binding.inputPw.editText?.text.toString()

        val loginRequest = LoginRequest(email = email, password = pw)
        viewModel.postLogin(loginRequest)

        handleLoginResponse()
    }

    private fun handleLoginResponse() {
        // 로그인 성공
        if (viewModel.postLogin.value?.status == STATUS_SUCCESS) {
            binding.inputPw.error = null
            navigateToMainActivity() // 화면 이동
            Log.d("success", "로그인 성공 : ${viewModel.postLogin.value}")
        }
        // 로그인 실패
        else {
            // 에러 메시지 설정
            val failureMessage = viewModel.failureMessage.value.toString()
            val userNotFoundMessage = extractErrorMessage(failureMessage)
            binding.inputPw.error = userNotFoundMessage
            Log.d("error", "error : ${userNotFoundMessage}")
        }
    }

    // 에러 문구 중 message 내용 추출
    private fun extractErrorMessage(response: String): String {
        val pattern = Regex("""\"message\":\"(.*?)\"""")
        val matchResult = pattern.find(response)
        return matchResult?.groupValues?.get(1) ?: ""
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
