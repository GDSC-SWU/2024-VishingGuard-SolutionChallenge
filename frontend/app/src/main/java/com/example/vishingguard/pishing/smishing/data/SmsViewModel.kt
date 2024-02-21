package com.example.vishingguard.pishing.smishing.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class SmsViewModel : ViewModel() {
    // userData
    val userId = UserData.getUserId()
    val accessToken = UserData.getToken()

    // Sms LiveData
    private val _postSms: MutableLiveData<SmsResponse> = MutableLiveData()  //read, write
    val postSms: LiveData<SmsResponse> = _postSms //read
    private val postSmsService = ServicePool.postSms

    // Server interaction
    fun postSms(smsRequest: SmsRequest) {
        if (accessToken != null && userId != null) {
            postSmsService.postSms(accessToken, userId, smsRequest).enqueue(object : retrofit2.Callback<SmsResponse> {
                override fun onResponse(call: Call<SmsResponse>, response: Response<SmsResponse>) {
                    if (response.isSuccessful) {
                        _postSms.value = response.body()
                        Log.d("success postSms", _postSms.value.toString())
                    } else {
                        Log.d("error postSms", "Failed response")
                    }
                }

                override fun onFailure(call: Call<SmsResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postSms", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}