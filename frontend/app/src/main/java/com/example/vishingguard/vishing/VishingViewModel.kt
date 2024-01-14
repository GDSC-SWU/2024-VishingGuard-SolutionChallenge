package com.example.vishingguard.vishing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import retrofit2.Call
import retrofit2.Response

class VishingViewModel : ViewModel() {
    // LiveData 객체 선언
    private val _postVishing: MutableLiveData<String> = MutableLiveData()  //read, write
    private val postVishingService = ServicePool.postVishing

    // 서버 통신
    fun postVishing(audio: String) {
        postVishingService.postVishing(audio).enqueue(object : retrofit2.Callback<VishingResponse> {
            override fun onResponse(call: Call<VishingResponse>, response: Response<VishingResponse>) {
                if (response.isSuccessful) {
                    _postVishing.value = response.body().toString()
                    Log.d("success", _postVishing.value.toString())
                } else {
                    Log.d("error", "실패한 응답")
                }
            }

            override fun onFailure(call: Call<VishingResponse>, t: Throwable) {
                t.message?.let { Log.d("error", it) } ?: "서버통신 실패(응답값 X)"
            }
        })
    }
}