package com.subhajeet.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.database.RestaurantEntity

class FavouriteRecyclerAdapter(val context: Context,val restaurantList: List<RestaurantEntity>) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>(){
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view){

        val txtRestaurantName: TextView = view.findViewById(R.id.txtFavRestaurantName)
        val txtRestaurantPrice: TextView = view.findViewById(R.id.txtFavRestaurantPrice)
        val txtRestaurantRating: TextView = view.findViewById(R.id.txtFavRestaurantRating)
        val imgRestaurantImage: ImageView = view.findViewById(R.id.imgFavRestaurantImage)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row,parent,false)

        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val restaurant = restaurantList[position]
        holder.txtRestaurantName.text = restaurant.restaurantName
        holder.txtRestaurantPrice.text = restaurant.restaurantPrice
        holder.txtRestaurantRating.text = restaurant.restaurantRating

        //holder.imgRestaurantImage.setImageResource(book.restaurantImage)

        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.default_image)
            .into(holder.imgRestaurantImage)
    }
}