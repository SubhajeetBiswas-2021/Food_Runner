package com.subhajeet.foodrunner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.adapter.FaqAdapter


class FAQsFragment : Fragment() {



    lateinit var layoutManager: RecyclerView.LayoutManager


    lateinit var recyclerFaq: RecyclerView
    private lateinit var recyclerAdapter: FaqAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f_a_qs, container, false)

        // Sample FAQ list
        val faqList = listOf(
            Pair("What is this app for?", "This app helps you manage tasks efficiently."),
            Pair("How do I register?", "Go to the registration page and fill out the form."),
            Pair("Can I reset my password?", "Yes, use the 'Forgot Password' option."),
            Pair("How do I contact support?", "You can contact us at support@example.com."),
            Pair("Is this app free?", "Yes, this app is completely free to use.")
        )

        recyclerFaq = view.findViewById(R.id.recyclerFaq)

        // Initialize the adapter with the FAQ list
        recyclerAdapter = FaqAdapter(faqList)

        // Set up the RecyclerView with a LinearLayoutManager
        recyclerFaq.layoutManager = LinearLayoutManager(context)
        recyclerFaq.adapter = recyclerAdapter

        return view
    }


}