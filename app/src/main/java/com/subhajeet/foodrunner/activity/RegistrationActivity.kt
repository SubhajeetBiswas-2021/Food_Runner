package com.subhajeet.foodrunner.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.databinding.ActivityRegisterBinding

class RegistrationActivity : AppCompatActivity() {

    lateinit var txtName:EditText
    lateinit var Email:EditText
    lateinit var MobNumber:EditText
    lateinit var DeliveryAddress:EditText
    lateinit var txtPassword:EditText
    lateinit var btnRegister:Button
    lateinit var fragment_container:FrameLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityRegisterBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
       // setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this,Activitylogin::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener{
            val email = binding.Email.text.toString()
            val password = binding.txtPassword.text.toString()

            val mobileNumber= binding.MobNumber.text.toString()
            val name = binding.txtName.text.toString()
            val deliveryAddress = binding.DeliveryAddress.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty())
                Activitylogin.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){

                        val editor = sharedPreferences.edit()
                        editor.putString("name", name)
                        editor.putString("email", email)
                        editor.putString("mobileNumber", mobileNumber)
                        editor.putString("deliveryAddress", deliveryAddress)
                        editor.putString("password", password)
                        editor.apply()

                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()

                        // Load the MyProfileFragment
                       // val profileFragment = MyProfileFragment()
                       // supportFragmentManager.beginTransaction()
                         //   .replace(R.id.fragment_container, profileFragment)
                           // .addToBackStack(null)
                         //   .commit()

                       startActivity(Intent(this,Activitylogin::class.java))           //was included
                        finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }

        txtName = findViewById(R.id.txtName)
        Email = findViewById(R.id.Email)
        MobNumber = findViewById(R.id.MobNumber)
        DeliveryAddress = findViewById(R.id.DeliveryAddress)
        txtPassword = findViewById(R.id.txtPassword)
        btnRegister = findViewById(R.id.btnRegister)


      //  val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

       /* btnRegister.setOnClickListener {

            // Retrieve entered data
            val mobileNumber = MobNumber.text.toString()
            val password = txtPassword.text.toString()
            val name = txtName.text.toString()
            val deliveryAddress = DeliveryAddress.text.toString()
            val email = Email.text.toString()

            if (name.isNotEmpty() || email.isNotEmpty() || mobileNumber.isNotEmpty()  || deliveryAddress.isNotEmpty()) {

                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.putString("email", email)
                editor.putString("mobileNumber", mobileNumber)
                editor.putString("password", password)

                editor.putString("deliveryAddress",deliveryAddress)

                editor.apply()
                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

           /* val profileFragment = MyProfileFragment()
            val bundle = Bundle()

                // Create an Intent

            // Pass data to the next screen using Intent extras
            bundle.putString("mobileNumber", mobileNumber)
            bundle.putString("password", password)
            bundle.putString("name", name)
            bundle.putString("deliveryAddress",deliveryAddress)
            bundle.putString("email", email)

            profileFragment.arguments = bundle


            fragment_container.visibility = View.VISIBLE

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .addToBackStack(null)
                .commit() */
        }*/



    }
}