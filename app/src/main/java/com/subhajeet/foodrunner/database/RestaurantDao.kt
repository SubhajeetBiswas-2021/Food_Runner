package com.subhajeet.foodrunner.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Insert
    fun insertRestaurant(restaurantEntity: RestaurantEntity)                //use to add a book to a table

    @Delete
    fun deleteRestaurant(restaurantEntity: RestaurantEntity)


    @Query("SELECT * FROM restaurant")                        // for favourite fragment to display all the entries of the table i.e all that are stored in the database
    fun getAllRestaurants():List<RestaurantEntity>


    @Query("SELECT * FROM restaurant WHERE restaurant_id = :restaurantId")    // to check wheather a particular book is added to favourites or not
    fun getRestaurantById(restaurantId: String): RestaurantEntity

    @Query("UPDATE restaurant SET is_liked = :isLiked WHERE restaurant_id = :id")
    fun updateFavoriteStatus(id: Int, isLiked: Boolean)

}