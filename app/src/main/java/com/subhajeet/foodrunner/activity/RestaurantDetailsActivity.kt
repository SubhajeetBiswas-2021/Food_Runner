package com.subhajeet.foodrunner.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.adapter.RestaurantMenuAdapter
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


    }

    /*private fun proceedToCart() {

        /*Here we see the implementation of Gson.
        * Whenever we want to convert the custom data types into simple data types
        * which can be transferred across for utility purposes, we will use Gson*/
        val gson = Gson()

        /*With the below code, we convert the list of order items into simple string which can be easily stored in DB*/
        val foodItems = gson.toJson(orderList)

        val async = ItemsOfCart(activity as Context, resId.toString(), foodItems, 1).execute()
        val result = async.get()
        if (result) {
            val data = Bundle()
            data.putInt("resId", resId as Int)
            data.putString("resName", resName)
            val intent = Intent(activity, CartActivity::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        } else {
            Toast.makeText((activity as Context), "Some unexpected error", Toast.LENGTH_SHORT)
                .show()
        }

    }

    class ItemsOfCart(
        context: Context,
        val restaurantId: String,
        val foodItems: String,
        val mode: Int
    ) : AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()


        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    db.orderDao().insertOrder(OrderEntity(restaurantId, foodItems))
                    db.close()
                    return true
                }

                2 -> {
                    db.orderDao().deleteOrder(OrderEntity(restaurantId, foodItems))
                    db.close()
                    return true
                }
            }

            return false
        }

    }*/
}

