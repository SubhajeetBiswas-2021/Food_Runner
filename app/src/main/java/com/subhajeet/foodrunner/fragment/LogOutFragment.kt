package com.subhajeet.foodrunner.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.activity.Activitylogin

class LogOutFragment : Fragment() {

    private lateinit var txtFAQs: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_out, container, false)

        // Initialize sharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(
            "FoodRunnerPreferences",
            Context.MODE_PRIVATE
        )

        txtFAQs = view.findViewById(R.id.txtFAQs)

        txtFAQs.setOnClickListener {
            showLogoutConfirmation()
        }

        return view
    }

    private fun showLogoutConfirmation() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes") { _, _ ->
            // Clear preferences and redirect to login
            sharedPreferences.edit().clear().apply()
            val intent = Intent(requireContext(), Activitylogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            // Dismiss the dialog
            dialog.dismiss()
        }
        builder.create().show()
    }
}
