package com.subhajeet.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.model.Restaurant

class HomeRecyclerAdapter(val context: Context, val itemList:ArrayList<Restaurant>):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtRestaurantName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtRestaurantPrice: TextView = view.findViewById(R.id.txtRestaurantPrice)
        val txtRestaurantRating: TextView = view.findViewById(R.id.txtRestaurantRating)
        val imgRestaurantImage: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row,parent,false)
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

        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.default_image).into(holder.imgRestaurantImage)

        holder.llContent.setOnClickListener{
            Toast.makeText(context,"Clicked on ${holder.txtRestaurantName.text}", Toast.LENGTH_SHORT).show()
        }
    }
}