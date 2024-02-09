package com.example.vishingguard.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentSignUpBinding
import com.example.vishingguard.login.data.LoginViewModel
import com.example.vishingguard.login.data.SignUpRequest

class SignUpFragment : BindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    val viewModel by viewModels<LoginViewModel>()

    companion object {
        const val STATUS_SUCCESS = 200
    }

    override fun initView() {
        // 뒤로가기
        binding.btnBack.setOnClickListener {
            navigateBack()
        }

        // 회원가입
        binding.btnNext.setOnClickListener {
            postSignUp()
        }
    }
    // 서버 연결
    private fun postSignUp(){
        val username = binding.inputName.editText?.text.toString()
        val email = binding.inputEmail.editText?.text.toString()
        val pw = binding.inputCheckPw.editText?.text.toString()

        val signUpRequest = SignUpRequest(username= username, email= email, password = pw)
        viewModel.postSignUp(signUpRequest)

        handleSignUpResponse()
    }
    private fun handleSignUpResponse(){
        // 회원가입 성공
        if (viewModel.postSignUp.value?.status == STATUS_SUCCESS) {
            binding.inputPw.error = null
            replaceFragment(EmailLoginFragment())  // 화면 이동
            Toast.makeText(requireContext(), "Sign-up successful", Toast.LENGTH_SHORT).show()
            Log.d("success", "회원가입 성공 : ${viewModel.postSignUp.value}")
        }
        // 회원가입 실패
        else {
            // 에러 메시지 설정
            val failureMessage = viewModel.failureMessage.value.toString()
            val userNotFoundMessage = extractErrorMessage(failureMessage)
            binding.inputCheckPw.error = userNotFoundMessage
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
    fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_login, fragment)
            .addToBackStack(null)
            .commit()
    }
}