package com.taghda.workingtestapplication.repositories

import androidx.lifecycle.LiveData
import com.taghda.workingtestapplication.data.local.ShoppingItem
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse

interface ShoppingRepository {

    fun insertShoppingItem(shoppingItem: ShoppingItem)

    fun observeTupleByPageoffset(pageOffset :Int): LiveData<ShoppingItem>

    fun searchForImage(): ExchangeRateResponse
}