package com.example.vishingguard

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vishingguard.base.BindingActivity
import com.example.vishingguard.databinding.ActivityMainBinding
import com.example.vishingguard.pishing.smishing.data.SmsDialog
import com.example.vishingguard.pishing.smishing.data.SmsRequest
import com.example.vishingguard.pishing.smishing.data.SmsViewModel
import com.example.vishingguard.stt.RequestConfig
import com.example.vishingguard.stt.SttData
import com.example.vishingguard.stt.SttViewModel
import com.example.vishingguard.vishing.CallDialog
import com.example.vishingguard.vishing.VoiceRequest
import com.example.vishingguard.vishing.VoiceViewModel
import java.io.File

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val SmsViewModel by viewModels<SmsViewModel>()
    private val SttViewModel by viewModels<SttViewModel>()
    private val VoiceViewModel by viewModels<VoiceViewModel>()

    private lateinit var telephonyManager: TelephonyManager

    override fun initView() {
        // Set up the screen
        setNavigation()

        // Check for phone phishing
        postStt()

        // Request necessary permissions
        requestPermission()

        // Request permissions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        // Register the phone state receiver
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        registerReceiver(phoneStateReceiver, IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
    }

    private fun setNavigation() {
        // Find the navigation controller
        val navController =
            supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()

        // Set up bottom navigation with navigation controller
        navController?.let {
            binding.botNavMain.setupWithNavController(navController)
            // Prevent changing icon theme color
            binding.botNavMain.itemIconTintList = null
        }
    }

    // Request permission to receive SMS
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECEIVE_SMS), 0)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Process the incoming SMS
        postSms(intent)
    }

    // Send the received SMS data to the ViewModel for processing
    private fun postSms(intent: Intent?) {
        if (intent != null) {
            // Extract SMS content and sender's phone number
            val smishingScript = intent.getStringExtra("content").toString()
            val phone = intent.getStringExtra("sender").toString()

            // Send SMS data to ViewModel for processing
            val smsRequest = SmsRequest(
                smishingScript = smishingScript, phone = phone)
            SmsViewModel.postSms(smsRequest)

            handleReportResponse()
        }
    }

    private fun handleReportResponse() {
        // Observe Vishing data
        SmsViewModel.postSms.observe(this) { response ->
            if (response.data) {
                val dialog = SmsDialog(this)
                dialog.show()
            }
        }
    }

    private val phoneStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val state = it.getStringExtra(TelephonyManager.EXTRA_STATE)
                when (state) {
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        // Show dialog when there's an incoming call
                        val callDialog = CallDialog(this@MainActivity)
                        callDialog.show()
                    }
                    TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                        // Start recording when call starts
                        // Add code to send notification 30 minutes later
                        Handler().postDelayed({
                            sendNotification()
                        }, 1800000)
                    }
                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        // Stop recording when call ends
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(phoneStateReceiver)
    }

    private fun sendNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "channel_id"
        val channelName = "channel_name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            channel.enableVibration(true) // Enable vibration
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.title_notification))
            .setContentText(getString(R.string.content_notification))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000)) // Set vibration pattern

        notificationManager.notify(112345648, notificationBuilder.build())

        // Check and request notification access permission
        if (!isNotificationAccessEnabled()) {
            // If notification access permission is not granted, navigate to settings to request it
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        }
    }
    private fun isNotificationAccessEnabled(): Boolean {
        val contentResolver = applicationContext.contentResolver
        val listeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val packageName = applicationContext.packageName
        return listeners != null && listeners.contains(packageName)
    }

    private fun postStt() {
        SttViewModel.postStt()

        // Observe Vishing data
        SttViewModel.postStt.observe(this) { response ->
            if (response.accessToken != null) {
                SttData.setSttAccessToken(response.accessToken)
            }
            postTranscribe()
        }
    }

    private fun postTranscribe() {
        // Set file path and file type
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/2.mp3"
        val file = File(filePath)

        val config = RequestConfig(
            true, 2, false, true,
            true, false, true, 50, "CALL",
            false, arrayListOf("Prosecutor", "Police")
        )
        SttViewModel.postTranscribe(config, file)

        // Observe Vishing data
        SttViewModel.postTranscribe.observe(this) { response ->
            if (response.id != null) {
                SttData.setTranscribeId(response.id)
                startPollingTranscribe()
            }
        }
    }

    private fun startPollingTranscribe() {
        SttViewModel.getTranscribe()

        SttViewModel.getTranscribe.observe(this) { response ->
            if (response.status == "completed") {
                response.results?.let { results ->
                    results.utterances.forEachIndexed { index, utterance ->

                        // FastAPI 연결
                        val voiceRequest = VoiceRequest(utterance.msg)
                        VoiceViewModel.postVoice(voiceRequest)
                        handleResponse()
                        Log.d("Utterance $index", utterance.msg)
                    }
                }
            }
        }
    }

    private fun handleResponse() {
        // Observe Vishing data
        VoiceViewModel.postVoice.observe(this) { response ->
            if (response.is_phishing) {
            }
        }
    }
}