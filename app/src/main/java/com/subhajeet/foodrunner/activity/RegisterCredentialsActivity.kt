package com.subhajeet.foodrunner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.subhajeet.foodrunner.R

class RegisterCredentialsActivity : AppCompatActivity() {
    lateinit var txtDisplayName:TextView
    lateinit var txtDisplayEmailAddress:TextView
    lateinit var txtDisplayMobileNumber:TextView
    lateinit var txtDisplayDeliveryAddress:TextView
    lateinit var txtDisplayPassword:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_credentials)

        txtDisplayName = findViewById(R.id.txtDisplayName)
        txtDisplayEmailAddress= findViewById(R.id.txtDisplayEmailAddress)
        txtDisplayMobileNumber= findViewById(R.id.txtDisplayMobileNumber)
        txtDisplayDeliveryAddress= findViewById(R.id.txtDisplayDeliveryAddress)
        txtDisplayPassword= findViewById(R.id.txtDisplayPassword)

        val mobileNumber = intent.getStringExtra("mobileNumber")
        val EmailAddress = intent.getStringExtra("email")
        val Password = intent.getStringExtra("password")
        val DeliveryAddress = intent.getStringExtra("delivery address")
        val Name = intent.getStringExtra("name")

        txtDisplayMobileNumber.text = mobileNumber
        txtDisplayEmailAddress.text = EmailAddress
        txtDisplayPassword.text = Password
        txtDisplayDeliveryAddress.text = DeliveryAddress
        txtDisplayName.text = Name
    }
}