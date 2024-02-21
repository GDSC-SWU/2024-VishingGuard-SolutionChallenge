package com.example.vishingguard.stt

object SttData {
    private var sttAccessToken: String? = null
    private var transcribeId: String? = null
    fun setSttAccessToken(accessToken: String) {
        this.sttAccessToken = "Bearer $accessToken"
    }

    fun setTranscribeId(transcribeId: String) {
        this.transcribeId = transcribeId
    }
    fun getSttAccessToken(): String? {
        return sttAccessToken
    }

    fun getTranscribeId(): String? {
        return transcribeId
    }
}