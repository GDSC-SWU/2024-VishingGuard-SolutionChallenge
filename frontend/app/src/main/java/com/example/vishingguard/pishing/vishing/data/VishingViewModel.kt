package com.example.vishingguard.pishing.vishing.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class VishingViewModel : ViewModel() {
    // userData
    val userId = UserData.getUserId()
    val accessToken = UserData.getToken()

    // Sms LiveData
    private val _postCall: MutableLiveData<VishingResponse> = MutableLiveData()  //read, write
    val postCall: LiveData<VishingResponse> = _postCall //read
    private val postCallService = ServicePool.postCall

    // Server interaction
    fun postCall(vishingRequest: VishingRequest) {
        if (accessToken != null && userId != null) {
            postCallService.postCall(accessToken, userId, vishingRequest).enqueue(object : retrofit2.Callback<VishingResponse> {
                override fun onResponse(call: Call<VishingResponse>, response: Response<VishingResponse>) {
                    if (response.isSuccessful) {
                        _postCall.value = response.body()
                        Log.d("success postCall", _postCall.value.toString())
                    } else {
                        Log.d("error postCall", "Failed response")
                    }
                }
                override fun onFailure(call: Call<VishingResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postCall", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}