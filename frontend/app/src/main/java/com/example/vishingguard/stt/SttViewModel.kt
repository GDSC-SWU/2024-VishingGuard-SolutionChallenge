package com.example.vishingguard.stt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import com.example.vishingguard.BuildConfig

class SttViewModel : ViewModel() {
    // userData
    val clientId = BuildConfig.clientId
    val clientSecret = BuildConfig.clientSecret

    // Vishing LiveData
    private val _postStt: MutableLiveData<SttResponse> = MutableLiveData()
    val postStt: LiveData<SttResponse> = _postStt
    private val sttService = SttServicePool.postStt

    private val _postTranscribe: MutableLiveData<TranscribeResponse> = MutableLiveData()
    val postTranscribe: LiveData<TranscribeResponse> = _postTranscribe
    private val transcribeService = SttServicePool.postTranscribe

    private val _getTranscribe: MutableLiveData<GetTranscribeResponse> = MutableLiveData()
    val getTranscribe: LiveData<GetTranscribeResponse> = _getTranscribe
    private val getTranscribeService = SttServicePool.getTranscribe

    fun postStt() {
        if (clientId != null) {
            sttService.authenticate(clientId, clientSecret).enqueue(object : retrofit2.Callback<SttResponse> {
                override fun onResponse(call: Call<SttResponse>, response: Response<SttResponse>) {
                    if (response.isSuccessful) {
                        _postStt.value = response.body()
                        Log.d("success postStt", _postStt.value.toString())
                    } else {
                        Log.d("error postStt", "Failed response")
                    }
                }

                override fun onFailure(call: Call<SttResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postStt", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }

    fun postTranscribe(config: RequestConfig, file: File) {
        val accessToken = SttData.getSttAccessToken()
        if (accessToken != null) {
            val filePart = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody("audio/*".toMediaTypeOrNull()))

            transcribeService.postTranscribe(accessToken, config, filePart).enqueue(object : retrofit2.Callback<TranscribeResponse> {
                override fun onResponse(call: Call<TranscribeResponse>, response: Response<TranscribeResponse>) {
                    if (response.isSuccessful) {
                        _postTranscribe.value = response.body()
                        Log.d("success postTranscribe", _postTranscribe.value.toString())
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d("error postTranscribe", errorBody ?: "Failed to get error body")
                    }
                }
                override fun onFailure(call: Call<TranscribeResponse>, t: Throwable) {
                    Log.d("error postTranscribe", t.message ?: "Failed server communication (no response)")
                }
            })
        }
    }

    fun getTranscribe() {
        val accessToken = SttData.getSttAccessToken()
        val transcribeId = SttData.getTranscribeId()
        if (accessToken != null && transcribeId != null) {
            getTranscribeService.getTranscribe(accessToken, transcribeId).enqueue(object : retrofit2.Callback<GetTranscribeResponse> {
                override fun onResponse(call: Call<GetTranscribeResponse>, response: Response<GetTranscribeResponse>) {
                    if (response.isSuccessful) {
                        _getTranscribe.value = response.body()
                        Log.d("success getTranscribe", _getTranscribe.value.toString())
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d("error getTranscribe", errorBody ?: "Failed to get error body")
                    }
                }
                override fun onFailure(call: Call<GetTranscribeResponse>, t: Throwable) {
                    Log.d("error getTranscribe", t.message ?: "Failed server communication (no response)")
                }
            })
        }
    }
}