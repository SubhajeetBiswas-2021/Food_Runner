package com.subhajeet.foodrunner.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.squareup.picasso.Picasso
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.database.RestaurantDatabase
import com.subhajeet.foodrunner.database.RestaurantEntity
import com.subhajeet.foodrunner.model.Restaurant

class HomeRecyclerAdapter(val context: Context, val itemList:ArrayList<Restaurant>):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtRestaurantName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtRestaurantPrice: TextView = view.findViewById(R.id.txtRestaurantPrice)
        val txtRestaurantRating: TextView = view.findViewById(R.id.txtRestaurantRating)
        val imgRestaurantImage: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val imgHeart: ImageView = view.findViewById(R.id.imgheart)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_single_row, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {


        val restaurant = itemList[position]
        holder.txtRestaurantName.text = restaurant.restaurantName
        holder.txtRestaurantPrice.text = restaurant.restaurantCost
        holder.txtRestaurantRating.text = restaurant.restaurantRating

        //holder.imgRestaurantImage.setImageResource(book.restaurantImage)

        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.default_image)
            .into(holder.imgRestaurantImage)

        holder.llContent.setOnClickListener {
            Toast.makeText(
                context,
                "Clicked on ${holder.txtRestaurantName.text}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Check if the restaurant is already in the favorites
        var restaurantId: String? = "100"
        val restaurantEntity = RestaurantEntity(
            restaurantId?.toInt() as Int,
            restaurantName = restaurant.restaurantName,
            restaurantRating = restaurant.restaurantRating,
            restaurantPrice = restaurant.restaurantCost,
            restaurantImage = restaurant.restaurantImage
        )

        val isFavorite = DBAsyncTask(context, restaurantEntity, 1).execute().get()

        if (isFavorite) {
            holder.imgHeart.setImageResource(R.drawable.red_heart_icon)
        } else {
            holder.imgHeart.setImageResource(R.drawable.grey_heart_icon)
        }

        holder.imgHeart.setOnClickListener {

            if (!DBAsyncTask(context, restaurantEntity, 1).execute()
                    .get()
            ) {  //This block will work when a book is not a favourite

                val async = DBAsyncTask(context, restaurantEntity, 2).execute()
                val result =
                    async.get()  //This variable will store true when the book will get saved as favourites and false when some error occurs

                if (result) {
                    Toast.makeText(
                        context,
                        "${restaurant.restaurantName} added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgHeart.setImageResource(R.drawable.red_heart_icon)
                }
            } else
            {
                val result = DBAsyncTask(context, restaurantEntity, 3).execute().get()
                if (result)
                {
                    Toast.makeText(
                        context,
                        "${restaurant.restaurantName} removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgHeart.setImageResource(R.drawable.grey_heart_icon)
                }else
                {
                    Toast.makeText(context,"Some error occurred!",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    class DBAsyncTask(val context:Context,val restaurantEntity: RestaurantEntity,val mode: Int): AsyncTask<Void, Void, Boolean>()
    {

        /*
        Mode 1-> Check DB if the book is favourite or not
        Mode 2-> Save the book into DB as favourite
        Mode 3-> Remove the favourite book
        */

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java,"restaurants-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {

            when(mode)
            {

                1 -> {

                    val restaurant: RestaurantEntity? = db.restaurantDao().getRestaurantById(restaurantEntity.restaurant_id.toString())
                    db.close()
                    return restaurant != null
                }

                2 -> {

                    db.restaurantDao().insertRestaurant(restaurantEntity)
                    db.close()
                    return true

                }

                3 -> {

                    db.restaurantDao().deleteRestaurant(restaurantEntity)
                    db.close()
                    return true

                }

            }
            return false
        }

    }
}