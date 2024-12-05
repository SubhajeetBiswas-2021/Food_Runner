package com.subhajeet.foodrunner.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.subhajeet.foodrunner.R

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)




        Handler().postDelayed({
            // Start the main activity after the specified delay
            val intent = Intent(this@SplashActivity, Activitylogin::class.java)
            startActivity(intent)
            finish() // Close the splash screen activity
        }, SPLASH_DELAY)
    }
}