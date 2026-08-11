package com.accounting.app.data.finance

import android.content.Context
import android.util.Log

class VoucherManager(private val context: Context) {
    private val TAG = "VoucherManager"

    fun processLocalVoucher(type: String, amount: Double, accountId: String, notes: String) {
        Log.d(TAG, "Processing local voucher type: $type, amount: $amount, account: $accountId, notes: $notes")
        // Queue to Room DB for offline sync
    }

    fun processLocalExpense(amount: Double, categoryId: String, notes: String) {
        Log.d(TAG, "Processing local expense: amount: $amount, category: $categoryId, notes: $notes")
        // Queue to Room DB for offline sync
    }
}
