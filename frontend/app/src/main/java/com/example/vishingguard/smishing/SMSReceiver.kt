package com.example.vishingguard.smishing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SMSReceiver : BroadcastReceiver() {

    // 문자가 왔을 때 실행
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals("android.provider.Telephony.SMS_RECEIVED")){
            val bundle = intent?.extras
            val messages = MessageParse(bundle!!)

            if(messages?.size!! > 0){
                val content = messages[0]?.messageBody.toString()
                if (content != null) {
                    // 문자 내용 전달
                    Log.d("message contents", content)
                    context?.let { sendToActivity(it, content) }
                }
            }
        }
    }

    // 문자 내용 추출
    fun MessageParse(bundle: Bundle): Array<SmsMessage?>? {
        val objs = bundle["pdus"] as Array<Any>?
        val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(objs!!.size)
        for (i in objs!!.indices) {
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
        }
        return messages
    }

    // 메시지 내용 전달
    private fun sendToActivity(context: Context, content: String) {
        val intent = Intent(context, SmishingActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        intent.putExtra("content", content)
        context.startActivity(intent)
    }
}