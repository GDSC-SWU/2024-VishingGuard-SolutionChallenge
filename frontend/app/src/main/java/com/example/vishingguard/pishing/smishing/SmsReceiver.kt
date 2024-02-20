package com.example.vishingguard.pishing.smishing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import com.example.vishingguard.MainActivity

@Suppress("DEPRECATION")
class SmsReceiver : BroadcastReceiver() {

    // When a message arrives
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals("android.provider.Telephony.SMS_RECEIVED")){
            // Extract the message from the intent's extras
            val bundle = intent?.extras
            val messages = MessageParse(bundle!!)

            // If there are messages
            if(messages?.size!! > 0){
                val sender = messages[0]?.originatingAddress
                val content = messages[0]?.messageBody.toString()

                if (sender != null) {
                    // pass data to the activity
                    context?.let { sendToActivity(it, sender, content) }
                }
            }
        }
    }

    // Extract message content
    fun MessageParse(bundle: Bundle): Array<SmsMessage?> {
        val objs = bundle["pdus"] as Array<*>?
        val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(objs!!.size)
        for (i in objs.indices) {
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
        }
        return messages
    }

    // Send message content to the activity
    private fun sendToActivity(context: Context, sender: String, content: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        intent.putExtra("sender", sender)
        intent.putExtra("content", content)
        context.startActivity(intent)
    }
}