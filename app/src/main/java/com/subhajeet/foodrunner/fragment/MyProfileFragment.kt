package com.subhajeet.foodrunner.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.subhajeet.foodrunner.R


class MyProfileFragment : Fragment() {

    lateinit var imgperson:ImageView
    lateinit var txtname: TextView
    lateinit var txtphone:TextView
    lateinit var txtemail:TextView
    lateinit var txtaddress:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_profile, container, false)

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
        // Retrieve the data from arguments
        val name = sharedPreferences.getString("name", "N/A")
        val email = sharedPreferences.getString("email", "N/A")
        val mobileNumber = sharedPreferences.getString("mobileNumber", "N/A")
        val deliveryAddress = sharedPreferences.getString("deliveryAddress", "N/A")

        imgperson = view.findViewById(R.id.imgperson)
        txtname = view.findViewById(R.id.txtname)
        txtphone = view.findViewById(R.id.txtphone)
        txtemail = view.findViewById(R.id.txtemail)
        txtaddress = view.findViewById(R.id.txtaddress)


        /*val args = arguments
        val name = args?.getString("name") ?: "Not Provided"
        val email = args?.getString("email") ?: "Not Provided"
        val mobileNumber = args?.getString("mobileNumber") ?: "Not Provided"
        val deliveryAddress = args?.getString("deliveryAddress") ?: "Not Provided"*/

        // Set the retrieved data to the TextViews
        txtname.text = name
        txtphone.text = mobileNumber
        txtemail.text = email
        txtaddress.text = deliveryAddress


        return view
    }

}