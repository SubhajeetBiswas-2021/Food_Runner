package com.subhajeet.foodrunner.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //fun insertOrder(orderEntity: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderEntity: OrderEntity): Long


    @Query("SELECT * FROM orders WHERE resId = :resId")
    fun getOrdersByResId(resId: String): List<OrderEntity>

    @Query("DELETE FROM orders WHERE resId = :resId")
    fun deleteOrders(resId: String)

    @Query("SELECT * FROM orders")
    fun getAllOrders(): List<OrderEntity>
}