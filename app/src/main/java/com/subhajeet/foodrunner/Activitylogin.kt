package com.subhajeet.foodrunner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class Activitylogin : AppCompatActivity() {
    lateinit var btnLogin:Button
    private lateinit var etMobileNumber:EditText
    private lateinit var etPassword:EditText
    private lateinit var SignUp:TextView
    private lateinit var forgotPassword:TextView

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val isLoggedIn =sharedPreferences.getBoolean("isLoggedIn",false)

        setContentView(R.layout.activity_login)

        if(isLoggedIn){
            val intent =Intent(this@Activitylogin,WelcomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


    etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        SignUp = findViewById(R.id.SignUp)
        forgotPassword = findViewById(R.id.forgotPassword)

        btnLogin.setOnClickListener {

            // Retrieve entered data
            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()
            savePreferences(mobileNumber,password)

            // Create an Intent
            val intent = Intent(this@Activitylogin,WelcomeScreenActivity::class.java)
            // Pass data to the next screen using Intent extras
            intent.putExtra("mobileNumber", mobileNumber)
            intent.putExtra("password", password)
            // Start the new activity
            startActivity(intent)
        }
        SignUp.setOnClickListener {

            val intent = Intent(this@Activitylogin,RegistrationActivity::class.java)
            startActivity(intent)
        }
        forgotPassword.setOnClickListener {

            val intent = Intent(this@Activitylogin,ForgotPassword::class.java)
            startActivity(intent)
        }
    }
    fun savePreferences(mobileNumber :String,password:String){
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
        sharedPreferences.edit().putString("MobileNumber", mobileNumber).apply()
        sharedPreferences.edit().putString("Password", password).apply()

    }



}