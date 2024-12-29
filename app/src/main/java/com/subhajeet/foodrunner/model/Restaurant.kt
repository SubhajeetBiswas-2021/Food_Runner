package com.subhajeet.foodrunner.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Restaurant (


    val restaurantId:String,
    val restaurantName: String,
    val restaurantRating: String,
    val restaurantCost:String,
    val restaurantImage: String,
    /*var isLiked: Boolean = false,*/
    var isFavorite: Boolean = false

)

class SharedViewModel : ViewModel() {
    val restaurants = MutableLiveData<MutableList<Restaurant>>(mutableListOf())
    val favoriteRestaurants = MutableLiveData<MutableList<Restaurant>>(mutableListOf())

    fun toggleFavorite(restaurant: Restaurant) {
        restaurant.isFavorite = !restaurant.isFavorite
        if (restaurant.isFavorite) {
            favoriteRestaurants.value?.add(restaurant)
        } else {
            favoriteRestaurants.value?.remove(restaurant)
        }
        favoriteRestaurants.value = favoriteRestaurants.value // Trigger observers
    }
}
