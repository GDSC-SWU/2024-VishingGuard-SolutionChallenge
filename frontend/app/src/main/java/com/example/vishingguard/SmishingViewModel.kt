package com.example.vishingguard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class SmishingViewModel : ViewModel() {
    // LiveData 객체 선언
    private val _postSmishing: MutableLiveData<String> = MutableLiveData()  //read, write
    //val postSmishing: LiveData<String> = _postSmishing //read
    private val postSmishingService = ServicePool.postSmishing

    // 서버 통신
    fun postSmishing(sms: String) {
        postSmishingService.postSmishing(sms).enqueue(object : retrofit2.Callback<SmishingResponse> {
            override fun onResponse(call: Call<SmishingResponse>, response: Response<SmishingResponse>) {
                if (response.isSuccessful) {
                    _postSmishing.value = response.body().toString()
                    Log.d("success", _postSmishing.value.toString())
                } else {
                    Log.d("error", "실패한 응답")
                }
            }

            override fun onFailure(call: Call<SmishingResponse>, t: Throwable) {
                t.message?.let { Log.d("error", it) } ?: "서버통신 실패(응답값 X)"
            }
        })
    }
}