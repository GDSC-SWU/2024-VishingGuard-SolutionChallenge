package com.example.vishingguard.vishing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
            // Call start
            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            }
            // Call end
            else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            }
        }
    }
}