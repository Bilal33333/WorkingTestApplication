package com.taghda.workingtestapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {
    val DATABASE_NAME = "shopping_db"

    abstract fun shoppingDao(): ShoppingDao

}