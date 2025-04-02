package com.subhajeet.foodrunner.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.adapter.CartItemAdapter
import com.subhajeet.foodrunner.database.OrderEntity
import com.subhajeet.foodrunner.database.RestaurantDatabase
import com.subhajeet.foodrunner.fragment.HomeFragment
import com.subhajeet.foodrunner.model.FoodItem
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerCart: RecyclerView
    private lateinit var cartItemAdapter: CartItemAdapter
    private var orderList = ArrayList<FoodItem>()
    private lateinit var txtResName: TextView
    private lateinit var rlLoading: RelativeLayout
    private lateinit var rlCart: RelativeLayout
    private lateinit var btnPlaceOrder: Button
    private var resId: String = ""
    private var resName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
        setUpCartList()
        placeOrder()
    }

    private fun init() {
        rlLoading = findViewById(R.id.rlLoading)
        rlCart = findViewById(R.id.rlCart)
        txtResName = findViewById(R.id.txtCartResName)
        val bundle = intent.getBundleExtra("data")
        resId = bundle?.getString("resId", "") as String
        resName = bundle.getString("resName", "") as String
        txtResName.text = resName
    }

    private fun setUpCartList() {
        recyclerCart = findViewById(R.id.recyclerCartItems)
        val dbList = GetItemsFromDBAsync(applicationContext).execute().get()

        // CHANGE: Extract food items from the database
        for (element in dbList) {
            orderList.addAll(
                Gson().fromJson(element.foodItems, Array<FoodItem>::class.java).asList()
            )
        }

        if (orderList.isEmpty()) {
            rlCart.visibility = View.GONE
            rlLoading.visibility = View.VISIBLE
        } else {
            rlCart.visibility = View.VISIBLE
            rlLoading.visibility = View.GONE
        }

        cartItemAdapter = CartItemAdapter(orderList, this)
        recyclerCart.layoutManager = LinearLayoutManager(this)
        recyclerCart.adapter = cartItemAdapter
    }

    private fun placeOrder() {
        btnPlaceOrder = findViewById(R.id.btnConfirmOrder)

        // CHANGE: Calculate the total cost
        var sum = 0
        for (i in 0 until orderList.size) {
            //sum += orderList[i].cost_for_one as Int
            sum += orderList[i].cost_for_one.toInt()
        }
        val total = "Place Order (Total: Rs. $sum)"
        btnPlaceOrder.text = total

        btnPlaceOrder.setOnClickListener {
            rlLoading.visibility = View.VISIBLE
            rlCart.visibility = View.INVISIBLE
          //  sendServerRequest()
            ClearDBAsync(applicationContext, resId).execute().get()    //have to edit
            // Notify the user and redirect to the homepage
            val dialog = Dialog(
                this@CartActivity,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen
            )
            dialog.setContentView(R.layout.order_placed_dialog)
            dialog.show()
            dialog.setCancelable(false)                                 //have to edit
            val btnOk = dialog.findViewById<Button>(R.id.btnOk)
            btnOk.setOnClickListener {
                dialog.dismiss()
                startActivity(Intent(this@CartActivity, MainActivity::class.java))
                ActivityCompat.finishAffinity(this@CartActivity)
            }                                                                 //have to edit
        }
    }




    private fun sendServerRequest() {
        val queue = Volley.newRequestQueue(this)

        // CHANGE: Create the JSON object for the request
        val jsonParams = JSONObject()
        jsonParams.put(
            "user_id",
            this@CartActivity.getSharedPreferences("FoodApp", Context.MODE_PRIVATE).getString(
                "user_id",
                null
            ) as String
        )
        jsonParams.put("restaurant_id", resId)
        var sum = 0
        for (i in 0 until orderList.size) {
            sum += orderList[i].cost_for_one.toInt()
        }
        jsonParams.put("total_cost", sum.toString())
        val foodArray = JSONArray()
        for (i in 0 until orderList.size) {
            val foodId = JSONObject()
            foodId.put("food_item_id", orderList[i].id)
            foodArray.put(i, foodId)
        }
        jsonParams.put("food", foodArray)

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST,
            "http://13.235.250.119/v2/place_order/fetch_result/",
            jsonParams,
            Response.Listener {
                try {
                    val data = it.getJSONObject("data")
                    val success = data.getBoolean("success")
                    if (success) {
                        // CHANGE: Clear the local database
                        ClearDBAsync(applicationContext, resId).execute().get()
                        // Notify the user and redirect to the homepage
                        val dialog = Dialog(
                            this@CartActivity,
                            android.R.style.Theme_Black_NoTitleBar_Fullscreen
                        )
                        dialog.setContentView(R.layout.order_placed_dialog)
                        dialog.show()
                        dialog.setCancelable(false)
                        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
                        btnOk.setOnClickListener {
                            dialog.dismiss()
                            startActivity(Intent(this@CartActivity, HomeFragment::class.java))
                            ActivityCompat.finishAffinity(this@CartActivity)
                        }
                    } else {
                        rlCart.visibility = View.VISIBLE
                        Toast.makeText(this@CartActivity, "Some Error occurred", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    rlCart.visibility = View.VISIBLE
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                rlCart.visibility = View.VISIBLE
                Toast.makeText(this@CartActivity, it.message, Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "8c6b73e37020cd" // Replace with your token

                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }

    // CHANGE: AsyncTask to fetch items from the local database
    class GetItemsFromDBAsync(context: Context) : AsyncTask<Void, Void, List<OrderEntity>>() {
        private val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<OrderEntity> {
            return db.orderDao().getAllOrders()
        }
    }

    // CHANGE: AsyncTask to clear the local database
    class ClearDBAsync(context: Context, val resId: String) : AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            db.orderDao().deleteOrders(resId)
            db.close()
            return true
        }
    }
}