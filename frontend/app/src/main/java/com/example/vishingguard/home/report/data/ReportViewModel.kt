package com.example.vishingguard.home.report.data

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
    val accessToken = UserData.getToken()

    // Report LiveData
    private val _getReport: MutableLiveData<ReportResponse> = MutableLiveData()  //read, write
    val getReport: LiveData<ReportResponse> = _getReport //read
    private val getReportService = ServicePool.getReport

    // Server interaction
    fun getReport() {
        if (accessToken != null) {
            getReportService.getReport(accessToken).enqueue(object : retrofit2.Callback<ReportResponse> {
                override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                    if (response.isSuccessful) {
                        _getReport.value = response?.body()
                        Log.d("success getReport", _getReport.value.toString())
                    } else {
                        Log.d("error getReport", "Failed response")
                    }
                }

                override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                    t.message?.let { Log.d("error getReport", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}