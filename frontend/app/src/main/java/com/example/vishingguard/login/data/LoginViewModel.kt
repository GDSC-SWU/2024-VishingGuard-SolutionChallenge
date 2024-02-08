package com.example.vishingguard.login.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import retrofit2.Call
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // SignUp LiveData
    private val _postSignUp: MutableLiveData<SignUpResponse> = MutableLiveData()  //read, write
    val postSignUp: LiveData<SignUpResponse> = _postSignUp //read
    private val postSignUpService = ServicePool.postSignUp

    // Failure LiveData
    private val _failureMessage: MutableLiveData<String> = MutableLiveData()
    val failureMessage: LiveData<String> = _failureMessage

    // Server interaction
    // SignUp
    fun postSignUp(signUpRequest: SignUpRequest) {
        postSignUpService.postSignUp(signUpRequest).enqueue(object : retrofit2.Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    _postSignUp.value = response.body()
                    Log.d("success SignUp", "통신 성공 : ${_postSignUp.value}")
                } else {
                    Log.d("error SignUp", "실패한 응답 : ${response.code()}")
                    response.errorBody()?.string()?.let {
                        _failureMessage.value = it
                        Log.d("error SignUp", it)
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.e("error SignUp", "서버 통신 실패", t)
                t.message?.let { Log.d("error SignUp", it) } ?: Log.d("error SignUp", "서버통신 실패(응답값 X)")
            }
        })
    }
}