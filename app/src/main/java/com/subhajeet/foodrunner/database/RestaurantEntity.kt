package com.subhajeet.foodrunner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")        //we must give name if not given it will take default  name of the class and this is known as annotation helps to tell the compiler that what we are creating
data class RestaurantEntity (
    @PrimaryKey val restaurant_id: Int,
    @ColumnInfo("restaurant_name") val restaurantName: String,
    @ColumnInfo("restaurant_price") val restaurantPrice:String,
    @ColumnInfo("restaurant_rating") val restaurantRating:String,
    @ColumnInfo("restaurant_image") val restaurantImage: String,
    @ColumnInfo("is_liked") val isLiked: Boolean = false

)