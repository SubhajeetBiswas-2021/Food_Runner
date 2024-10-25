package com.subhajeet.foodrunner

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class WelcomeScreenActivity : AppCompatActivity() {
     lateinit var welcomeTextMessage :TextView
     private lateinit var txtDisplayMobileNumber :TextView
     private lateinit var txtDisplayPassword :TextView
    lateinit var sharedPreferences: SharedPreferences
    var mobilenumber: String? ="9477092783"
    var password: String? ="9477092783"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_welcome_screen)

        txtDisplayMobileNumber = findViewById(R.id.txtDisplayMobileNumber)
        txtDisplayPassword = findViewById(R.id.txtDisplayPassword)

        mobilenumber = sharedPreferences.getString("MobileNumber","9477092783")
        password = sharedPreferences.getString("Password","123")
        txtDisplayMobileNumber.text= mobilenumber
        txtDisplayPassword.text = password

       /* welcomeMessage = findViewById(R.id.welcomeTextMessage)
        txtDisplayMobileNumber = findViewById(R.id.txtDisplayMobileNumber)
        txtDisplayPassword = findViewById(R.id.txtDisplayPassword)



        val mobileNumber = intent.getStringExtra("mobileNumber")
        val password = intent.getStringExtra("password")

        txtDisplayMobileNumber.text = mobileNumber
        txtDisplayPassword.text = password*/



    }
}