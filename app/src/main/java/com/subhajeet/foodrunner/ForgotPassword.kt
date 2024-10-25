package com.subhajeet.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ForgotPassword : AppCompatActivity() {
    lateinit var etMobileNumber:EditText
    lateinit var etemailadrs:EditText
    lateinit var btnNext:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etemailadrs = findViewById(R.id.etemailadrs)
        btnNext = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {

            // Retrieve entered data
            val mobileNumber = etMobileNumber.text.toString()
            val emailAddress = etemailadrs.text.toString()


            // Create an Intent
            val intent = Intent(this@ForgotPassword,ForgotCredentials::class.java)
            // Pass data to the next screen using Intent extras
            intent.putExtra("mobileNumber", mobileNumber)
            intent.putExtra("emailAddress", emailAddress)
            // Start the new activity
            startActivity(intent)
        }
    }
}