package com.example.vishingguard.pishing.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class ReportViewModel : ViewModel() {
    // userData
    val userId = UserData.getUserId()
    val accessToken = UserData.getToken()

    // Vishing LiveData
    private val _postVishing: MutableLiveData<VishingResponse> = MutableLiveData()  //read, write
    val postVishing: LiveData<VishingResponse> = _postVishing //read
    private val postVishingService = ServicePool.postVishing

    // Smishing LiveData
    private val _postSmishing: MutableLiveData<SmishingResponse> = MutableLiveData()  //read, write
    val postSmishing: LiveData<SmishingResponse> = _postSmishing //read
    private val postSmishingService = ServicePool.postSmishing

    // Server interaction
    fun postVishing() {
        if (accessToken != null && userId != null) {
            postVishingService.postVishing(accessToken, userId).enqueue(object : retrofit2.Callback<VishingResponse> {
                override fun onResponse(call: Call<VishingResponse>, response: Response<VishingResponse>) {
                    if (response.isSuccessful) {
                        _postVishing.value = response?.body()
                        Log.d("success postVishing", _postVishing.value.toString())
                    } else {
                        Log.d("error postVishing", "Failed response")
                    }
                }

                override fun onFailure(call: Call<VishingResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postVishing", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }

    fun postSmishing() {
        if (accessToken != null && userId != null) {
            postSmishingService.postSmishing(accessToken, userId).enqueue(object : retrofit2.Callback<SmishingResponse> {
                override fun onResponse(call: Call<SmishingResponse>, response: Response<SmishingResponse>) {
                    if (response.isSuccessful) {
                        _postSmishing.value = response.body()
                        Log.d("success postSmishing", _postSmishing.value.toString())
                    } else {
                        Log.d("error postSmishing", "Failed response")
                    }
                }

                override fun onFailure(call: Call<SmishingResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postSmishing", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}