package com.subhajeet.foodrunner.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.model.FoodItem

class RestaurantMenuAdapter(
    val context: Context, val menuList: ArrayList<FoodItem>
) : RecyclerView.Adapter<RestaurantMenuAdapter.MenuViewHolder>() {

    private val addedItems = mutableSetOf<String>() // Track added items by their IDs

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.tvItemName)
        val tvItemPrice: TextView = view.findViewById(R.id.tvItemPrice)
        val btnAddRemove: Button = view.findViewById(R.id.btnAddRemove)
       // val sno: TextView = view.findViewById(R.id.txtSNo)
       // val addToCart: Button = view.findViewById(R.id.btnAddToCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item, parent, false)

        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val menu = menuList[position]
        holder.tvItemName.text = menu.name
        holder.tvItemPrice.text = menu.cost_for_one
       // holder.sno.text = menu.id

        // Set the initial state of the button based on whether the item is added
        if (addedItems.contains(menu.id)) {
            holder.btnAddRemove.text = "Remove"
            holder.btnAddRemove.setBackgroundColor(Color.parseColor("#fbc02d"))
        } else {
            holder.btnAddRemove.text = "Add"
            holder.btnAddRemove.setBackgroundColor(Color.RED)
        }

        // Handle button clicks
        holder.btnAddRemove.setOnClickListener {
            if (addedItems.contains(menu.id)) {
                // Item is already added, so remove it
                addedItems.remove(menu.id)
                holder.btnAddRemove.text = "Add"
                holder.btnAddRemove.setBackgroundColor(Color.RED)
            } else {
                // Item is not added, so add it
                addedItems.add(menu.id)
                holder.btnAddRemove.text = "Remove"
                holder.btnAddRemove.setBackgroundColor(Color.parseColor("#fbc02d"))
            }
        }
    }
    // Function to get the list of added items
    fun getAddedItems(): List<FoodItem> {
        return menuList.filter { addedItems.contains(it.id) }
    }

    fun clearAddedItems() {
        addedItems.clear()
    }
}

