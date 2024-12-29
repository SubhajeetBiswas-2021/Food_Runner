package com.subhajeet.foodrunner.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.databinding.ActivityLoginBinding


class Activitylogin : AppCompatActivity() {
    lateinit var btnLogin:Button
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText
    private lateinit var SignUp:TextView
    private lateinit var forgotPassword:TextView

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityLoginBinding
    
    companion object{
        lateinit var auth:FirebaseAuth
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.activity_login)
        
        binding.SignUp.setOnClickListener{
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }



        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty())
                Activitylogin.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this,RegistrationActivity::class.java))
                    }
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE)
        val isLoggedIn =sharedPreferences.getBoolean("isLoggedIn",false)



        /*if(isLoggedIn){
            val intent =Intent(this@Activitylogin, MainActivity::class.java)
            startActivity(intent)
            finish()
        }*/


        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        SignUp = findViewById(R.id.SignUp)
        forgotPassword = findViewById(R.id.forgotPassword)

       /* btnLogin.setOnClickListener {

            // Retrieve entered data
           /* val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()
            savePreferences(mobileNumber,password)*/

            // Create an Intent
            val intent = Intent(this@Activitylogin, MainActivity::class.java)
            // Pass data to the next screen using Intent extras
          /*  intent.putExtra("mobileNumber", mobileNumber)
            intent.putExtra("password", password)*/
            // Start the new activity
            savePreferences()
           startActivity(intent)

        }*/
       /* SignUp.setOnClickListener {

            val intent = Intent(this@Activitylogin, RegistrationActivity::class.java)
            startActivity(intent)
        }*/
        forgotPassword.setOnClickListener {

            val intent = Intent(this@Activitylogin, ForgotPassword::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    /*fun savePreferences(mobileNumber :String,password:String){
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
        sharedPreferences.edit().putString("MobileNumber", mobileNumber).apply()
        sharedPreferences.edit().putString("Password", password).apply()*/
    fun savePreferences(){
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()

    }




}