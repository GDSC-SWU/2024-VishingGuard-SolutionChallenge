package com.example.vishingguard.vishing

import android.os.Bundle
import android.view.LayoutInflater
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.vishingguard.databinding.CallDialogBinding

class CallDialog(context: Context) : Dialog(context) {

    private lateinit var binding: CallDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout and set up
        binding = CallDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}