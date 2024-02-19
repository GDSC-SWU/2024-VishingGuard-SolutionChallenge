package com.example.vishingguard.home.data

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Date

class HomeViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()

    // Home LiveData
    private val _postHome: MutableLiveData<HomeResponse> = MutableLiveData()  // Read and Write
    val postHome: LiveData<HomeResponse> = _postHome // Read
    private val postHomeService = ServicePool.postHome

    private val getFssService = ServicePool.getFss

    // Server interaction
    // Post home data
    fun postHome() {
        if (accessToken != null) {
            postHomeService.postHome(accessToken).enqueue(object : retrofit2.Callback<HomeResponse> {
                override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                    if (response.isSuccessful) {
                        _postHome.value = response?.body()
                        Log.d("success postHome", _postHome.value.toString())
                    } else {
                        Log.d("error postHome", "Failed response")
                    }
                }

                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postHome", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }

    // Get FSS data
    fun getFss(number: Int) {
        if (accessToken != null) {
            getFssService.getFss(accessToken, number).enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            saveFile(it, number)
                        }
                        Log.d("success getFss", response.body().toString())
                    } else {
                        Log.d("error getFss", "Failed response")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.message?.let { Log.d("error getFss", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }

    // Save file from response
    fun saveFile(responseBody: ResponseBody, number: Int) {
        val fileName: String = Date().time.toString() + number + ".pdf"
        var outputPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName // Internal memory location
        val file = File(outputPath)

        val inputStream: InputStream = responseBody.byteStream()
        val outputStream = FileOutputStream(file)
        val buffer = ByteArray(4096)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }
        outputStream.close()
        inputStream.close()
        Log.d("success getFss", "File downloaded successfully.")
    }
}