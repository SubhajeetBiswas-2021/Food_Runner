package com.subhajeet.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.subhajeet.foodrunner.R

class RegistrationActivity : AppCompatActivity() {

    lateinit var txtName:EditText
    lateinit var Email:EditText
    lateinit var MobNumber:EditText
    lateinit var DeliveryAddress:EditText
    lateinit var txtPassword:EditText
    lateinit var btnRegister:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtName = findViewById(R.id.txtName)
        Email = findViewById(R.id.Email)
        MobNumber = findViewById(R.id.MobNumber)
        DeliveryAddress = findViewById(R.id.DeliveryAddress)
        txtPassword = findViewById(R.id.txtPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {

            // Retrieve entered data
            val mobileNumber = MobNumber.text.toString()
            val password = txtPassword.text.toString()
            val name = txtName.text.toString()
            val deliveryaddress = DeliveryAddress.text.toString()
            val email = Email.text.toString()


            // Create an Intent
            val intent = Intent(this@RegistrationActivity, RegisterCredentialsActivity::class.java)
            // Pass data to the next screen using Intent extras
            intent.putExtra("mobileNumber", mobileNumber)
            intent.putExtra("password", password)
            intent.putExtra("name", name)
            intent.putExtra("delivery address", deliveryaddress)
            intent.putExtra("email", email)
            // Start the new activity
            startActivity(intent)
        }



    }
}