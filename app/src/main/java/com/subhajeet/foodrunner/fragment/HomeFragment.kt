package com.subhajeet.foodrunner.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.adapter.HomeRecyclerAdapter
import com.subhajeet.foodrunner.database.RestaurantDatabase
import com.subhajeet.foodrunner.database.RestaurantEntity
import com.subhajeet.foodrunner.model.Restaurant
import com.subhajeet.foodrunner.util.ConnectionManager
import org.json.JSONException


class HomeFragment : Fragment() {



    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager:RecyclerView.LayoutManager



    //val bookList = arrayListOf("P.S.I love You","The Great Gatsby","Anna Karenina","Madame Bovary","War and Peace","Lolita","Middlemarch","The Adventure of Huckleberry Finn","Moby-Dick","The Lord of the Rings")

    lateinit var recyclerAdapter: HomeRecyclerAdapter

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar



    val restaurantInfoList = arrayListOf<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)


        progressLayout = view.findViewById(R.id.progressLayout)

        progressBar = view.findViewById(R.id.progressBar)

        progressLayout.visibility = View.VISIBLE





        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)  //variable for storing the queue of request

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"
        //it will give us the response

        if(ConnectionManager().checkConnectivity(activity as Context)){

            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,
                Response.Listener {

                    try{
                        progressLayout.visibility = View.GONE
                        val success = it.getJSONObject("data").getBoolean("success")

                        if(success){
                            val data = it.getJSONObject("data").getJSONArray("data")
                            for(i in 0 until data.length()){
                                val restaurantJsonObject = data.getJSONObject(i)
                                val restaurantObject = Restaurant(
                                    restaurantJsonObject.getString("id"),
                                    restaurantJsonObject.getString("name"),
                                    restaurantJsonObject.getString("rating"),
                                    restaurantJsonObject.getString("cost_for_one"),
                                    restaurantJsonObject.getString("image_url")
                                )
                                restaurantInfoList.add(restaurantObject)


                                recyclerAdapter = HomeRecyclerAdapter(activity as Context, restaurantInfoList)

                                recyclerDashboard.adapter = recyclerAdapter

                                recyclerDashboard.layoutManager = layoutManager





                            }
                        }else{
                            Toast.makeText(activity as Context,"Some error occurred!!", Toast.LENGTH_SHORT).show()
                        }
                    }catch (e:JSONException){
                        Toast.makeText(activity as Context,"Some Unexpected error occurred!!",Toast.LENGTH_SHORT).show()
                    }

                },
                Response.ErrorListener {

                    if(activity != null) {
                        Toast.makeText(
                            activity as Context,
                            "Volley error occured!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "8c6b73e37020cd"
                    return headers

                }

            }
            queue.add(jsonObjectRequest)

        }else{

            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open  Settings"){text,listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }

            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }


        return view
    }



}