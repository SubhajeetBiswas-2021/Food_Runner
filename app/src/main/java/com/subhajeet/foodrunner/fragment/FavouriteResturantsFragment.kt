package com.subhajeet.foodrunner.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.subhajeet.bookhub.adapter.FavouriteRecyclerAdapter
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.database.RestaurantDatabase
import com.subhajeet.foodrunner.database.RestaurantEntity
import com.subhajeet.foodrunner.model.SharedViewModel


class FavouriteResturantsFragment : Fragment() {

    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var sharedViewModel: SharedViewModel

    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    var dbRestaurantList =
        listOf<RestaurantEntity>()           //created variable to store the booklist


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite_resturants, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)

        layoutManager = GridLayoutManager(
            activity as Context,
            1
        )   //grid layout arranges items in a row and so here there will be 2 items in a row.


        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]




        return view
    }

    /*class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<RestaurantEntity>>(){
        override fun doInBackground(vararg params: Void?): List<RestaurantEntity> {
            val db = Room.databaseBuilder(context, RestaurantDatabase::class.java,"restaurants-db").build()    //Initialized the database

            return db.restaurantDao().getAllRestaurants()    //return the list of books
        }

    }*/

}