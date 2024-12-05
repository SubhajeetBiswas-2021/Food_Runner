package com.subhajeet.foodrunner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.subhajeet.foodrunner.R

class ForgotCredentials : AppCompatActivity() {
    lateinit var welcomeTextMessage:TextView
    lateinit var txtDisplayMobileNumber:TextView
    lateinit var txtDisplayEmailAddress:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_credentials)

        txtDisplayMobileNumber = findViewById(R.id.txtDisplayMobileNumber)
        txtDisplayEmailAddress= findViewById(R.id.txtDisplayEmailAddress)


        val mobileNumber = intent.getStringExtra("mobileNumber")
        val EmailAddress = intent.getStringExtra("emailAddress")

        txtDisplayMobileNumber.text = mobileNumber
        txtDisplayEmailAddress.text = EmailAddress
    }
}