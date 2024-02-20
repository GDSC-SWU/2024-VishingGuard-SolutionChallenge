package com.example.vishingguard.pishing.smishing.data

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.vishingguard.databinding.SmsDialogBinding

class SmsDialog(context: Context) : Dialog(context) {

    private lateinit var binding: SmsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout and set up
        binding = SmsDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}