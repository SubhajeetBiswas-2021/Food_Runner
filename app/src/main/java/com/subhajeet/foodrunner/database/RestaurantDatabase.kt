package com.subhajeet.foodrunner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RestaurantEntity::class], version = 1)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao
   /* abstract fun orderDao(): OrderDao*/

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        val MIGRATION_2_1 = object : Migration(2, 1) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Define SQL statements to migrate schema from version 2 to 1
                database.execSQL("ALTER TABLE your_table_name ADD COLUMN new_column_name TEXT DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): RestaurantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "your_database_name"
                )
                    .addMigrations(MIGRATION_2_1) // Add the migration here
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }
}