package com.example.vishingguard

import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vishingguard.base.BindingActivity
import com.example.vishingguard.databinding.ActivityMainBinding
import com.example.vishingguard.pishing.smishing.data.SmsDialog
import com.example.vishingguard.pishing.smishing.data.SmsRequest
import com.example.vishingguard.pishing.smishing.data.SmsViewModel

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<SmsViewModel>()

    override fun initView() {
        // Set up the screen
        setNavigation()

        // Request permissions for receiving SMS
        requestPermission()
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
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS), 0)
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
            viewModel.postSms(smsRequest)

            handleReportResponse()
        }
    }

    private fun handleReportResponse() {
        // Observe Vishing data
        viewModel.postSms.observe(this) { response ->
            if (response.data) {
                val dialog = SmsDialog(this)
                dialog.show()
            }
        }
    }
}