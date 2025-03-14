package com.subhajeet.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.subhajeet.foodrunner.R
import com.subhajeet.foodrunner.model.FoodItem

class RestaurantMenuAdapter(
    val context: Context, val menuList: ArrayList<FoodItem>
) : RecyclerView.Adapter<RestaurantMenuAdapter.MenuViewHolder>() {
    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.tvItemName)
        val tvItemPrice: TextView = view.findViewById(R.id.tvItemPrice)
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
    }
}

