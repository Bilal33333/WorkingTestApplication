package com.taghda.workingtestapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val TransactionNo: Int,
    val BalanceNo: Int,
    var OldBalance_USD: Int,
    var NewBalance_USD: Int,
    var NewBalance_EUR: Double,
    var ExchangeRate: Double
)