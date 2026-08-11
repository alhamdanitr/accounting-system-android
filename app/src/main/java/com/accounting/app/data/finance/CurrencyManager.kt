package com.accounting.app.data.finance

import android.content.Context
import android.util.Log

class CurrencyManager(private val context: Context) {
    private val TAG = "CurrencyManager"
    private var currentCurrency: String = "YER"
    private var exchangeRate: Double = 1.0

    fun setCurrency(currency: String, rate: Double) {
        currentCurrency = currency
        exchangeRate = rate
        Log.d(TAG, "Currency set to $currentCurrency with exchange rate $exchangeRate")
    }

    fun convertToLocal(amount: Double): Double {
        return amount * exchangeRate
    }
}
