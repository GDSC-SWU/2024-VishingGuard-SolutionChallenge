package com.example.vishingguard.smishing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.telephony.SmsMessage
import com.example.vishingguard.MainActivity
import java.util.Locale

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

                // Get current date and time
                val currentDateTime = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val date = dateFormat.format(currentDateTime)
                val time = timeFormat.format(currentDateTime)

                if (sender != null && date != null) {
                    // pass data to the activity
                    context?.let { sendToActivity(it, sender, content, date, time) }
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
    private fun sendToActivity(context: Context, sender: String, content: String, date: String, time: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        intent.putExtra("sender", sender)
        intent.putExtra("content", content)
        intent.putExtra("date", date)
        intent.putExtra("time", time)
        context.startActivity(intent)
    }
}