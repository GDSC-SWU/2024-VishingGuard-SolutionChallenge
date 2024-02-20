package com.example.vishingguard.home.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.R
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
                        _postHome.value = response.body()
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
    fun getFss(context: Context, number: Int) {
        if (accessToken != null) {
            getFssService.getFss(accessToken, number).enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            // After saving the file, display the notification
                            saveFile(context, it, number)
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
    fun saveFile(context: Context, responseBody: ResponseBody, number: Int) {
        val fileName: String = when (number) {
            0 -> "Voice phishing_Damage Prevention_10_Measures"
            1 -> "Financial_Fraudsters_Used_Words"
            2 -> "Report_ASAP_Fraudsters_Voice"
            else -> Date().time.toString()
        } + ".pdf"

        // After saving the file, display the notification
        showNotification(context, fileName)
        Log.d("showNotification","Called")

        val outputPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName // Internal memory location
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

    // Change the importance of the notification channel in the showNotification() method
    private fun showNotification(context: Context, fileName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "VishingGuard_CHANNEL_ID"
        val channelName = "VishingGuard Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH // Change the importance setting
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("File Downloaded")
            .setContentText(fileName)
            .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        Log.d("showNotification","Called2")
    }
}