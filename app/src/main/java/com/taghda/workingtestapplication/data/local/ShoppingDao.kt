package com.taghda.workingtestapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taghda.workingtestapplication.data.local.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items WHERE TransactionNo LIKE :pageOffset")
    fun observeTupleByPageoffset(pageOffset :Int): LiveData<ShoppingItem>

}