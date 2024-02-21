package com.example.vishingguard.vishing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class VoiceViewModel : ViewModel() {
    // Vishing LiveData
    private val _postVoice: MutableLiveData<VoiceResponse> = MutableLiveData()
    val postVoice: LiveData<VoiceResponse> = _postVoice
    private val postVoiceService = ServicePool.postVoice

    fun postVoice(voice : VoiceRequest) {
        if (voice != null) {
            postVoiceService.postVoice(voice).enqueue(object : retrofit2.Callback<VoiceResponse> {
                override fun onResponse(call: Call<VoiceResponse>, response: Response<VoiceResponse>) {
                    if (response.isSuccessful) {
                        _postVoice.value = response.body()
                        Log.d("success postVoice", _postVoice.value.toString())
                    } else {
                        Log.d("error postVoice", "Failed response")
                    }
                }

                override fun onFailure(call: Call<VoiceResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postVoice", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}