package com.subhajeet.foodrunner.activity


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.adapter.RestaurantMenuAdapter
import com.subhajeet.foodrunner.database.OrderEntity
import com.subhajeet.foodrunner.database.RestaurantDatabase
import com.subhajeet.foodrunner.model.FoodItem

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    lateinit var recyclerMenuItems: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    lateinit var recyclerAdapter: RestaurantMenuAdapter
    lateinit var btnAddRemove:Button
    private var orderList = arrayListOf<FoodItem>()             //new

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var goToCart: Button                   //new
        var resId: Int? = 0
        var resName: String? = ""
    }

    //var id: Int? = 0
    var id: String? = null
  /*  val restaurantMenuList = arrayListOf<FoodItem>(
        FoodItem("hi", "234"),
        FoodItem("ho", "234"),
        FoodItem("lo", "234"),
        FoodItem("hi", "234"),
        FoodItem("lol", "234"),
        FoodItem("toll", "234"),
        FoodItem("poll", "234"),
        FoodItem("loip", "234"),
        FoodItem("bho", "234"),
        FoodItem("kno", "234")
    )*/
  val restaurantMenuList = arrayListOf<FoodItem>()


    private lateinit var btnGoToCart: Button

    @SuppressLint("RestrictedApi")
    private lateinit var menuAdapter: MenuAdapter
    private val cartItems = mutableListOf<MenuItem>()
    val restaurantInfoList = arrayListOf<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

       // id = intent?.getIntExtra("restaurant_id", 0) ?: 0
        id = intent?.getStringExtra("restaurant_id")


        //  btnGoToCart = findViewById(R.id.btnGoToCart)

        recyclerMenuItems = findViewById(R.id.recyclerMenuItems)
        layoutManager = LinearLayoutManager(this@RestaurantDetailsActivity)



        val queue = Volley.newRequestQueue(this@RestaurantDetailsActivity)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$id"

        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                val success = it.getJSONObject("data").getBoolean("success")

                if(success){
                    val data = it.getJSONObject("data").getJSONArray("data")
                    for(i in 0 until data.length()){
                        val menuJsonObject = data.getJSONObject(i)
                        val menuObject = FoodItem(
                            menuJsonObject.getString("id"),
                            menuJsonObject.getString("name"),
                            menuJsonObject.getString("cost_for_one"),
                            menuJsonObject.getString("restaurant_id")
                        )
                        restaurantMenuList.add(menuObject)
                        recyclerAdapter = RestaurantMenuAdapter(this, restaurantMenuList)

                        recyclerMenuItems.adapter = recyclerAdapter
                        recyclerMenuItems.layoutManager = layoutManager

                        recyclerMenuItems.addItemDecoration(
                            DividerItemDecoration(
                                recyclerMenuItems.context, (layoutManager as LinearLayoutManager)
                                    .orientation
                            )
                        )

                    }
                }else{
                    Toast.makeText(this@RestaurantDetailsActivity,"Some Error Occured!!!",Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {

            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "8c6b73e37020cd"
                    return headers
                }

            }
        queue.add(jsonObjectRequest)

        // Handle the "Proceed to Cart" button click
        goToCart = findViewById(R.id.btnGoToCart)
        goToCart.setOnClickListener {
            val addedItems = recyclerAdapter.getAddedItems()
            if (addedItems.isNotEmpty()) {
                // Proceed to cart with the added items
                // You can pass the addedItems list to the next activity or perform any other action
                // Save items to the local database
                SaveToDBAsync(applicationContext, addedItems, resId.toString()).execute()
                // Navigate to CartActivity
                val intent = Intent(this, CartActivity::class.java)
                val bundle = Bundle()
                //bundle.putInt("resId", resId as Int)
                bundle.putString("resId", "$resId")
                bundle.putString("resName", resName)
                intent.putExtra("data", bundle)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please add items to the cart", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBackPressed() {
        // Clear the added items when the user navigates back
        recyclerAdapter.clearAddedItems()
        super.onBackPressed()
    }

    // AsyncTask to save items to the local database
    class SaveToDBAsync(
        context: Context,
        private val foodItems: List<FoodItem>,
        private val resId: String
    ) : AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            val foodItemsJson = Gson().toJson(foodItems) // Convert list to JSON string
            val orderEntity = OrderEntity(resId, foodItemsJson)
            db.orderDao().insertOrder(orderEntity)
            db.close()
            return true
        }
    }




}

