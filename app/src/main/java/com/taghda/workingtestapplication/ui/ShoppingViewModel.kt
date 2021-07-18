package com.taghda.workingtestapplication.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taghda.workingtestapplication.data.local.ShoppingItem
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse
import com.taghda.workingtestapplication.repositories.DefaultNfcHelperReppository
import com.taghda.workingtestapplication.repositories.NfcHelperRepository
import com.taghda.workingtestapplication.repositories.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.Exception

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository,
    private val nfcHelperReppository: NfcHelperRepository
) : ViewModel() {

    private val _price = MutableLiveData<ShoppingItem>()
    val price: LiveData<ShoppingItem> = _price

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) {
        viewModelScope.launch{
            repository.insertShoppingItem(shoppingItem)
        }
    }
    fun getRates_from_api() : ExchangeRateResponse{
        return repository.searchForImage()

    }
}













