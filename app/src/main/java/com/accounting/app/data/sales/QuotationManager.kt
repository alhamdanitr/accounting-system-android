package com.accounting.app.data.sales

import android.content.Context
import android.util.Log

class QuotationManager(private val context: Context) {
    private val TAG = "QuotationManager"

    fun saveLocalQuotation(customerId: String, itemsCount: Int, total: Double) {
        Log.d(TAG, "Saving local quotation for customer $customerId with $itemsCount items, total: $total")
        // Queue to Room DB for offline sync
    }

    fun convertQuotationToInvoice(quotationId: String) {
        Log.d(TAG, "Converting quotation $quotationId to sales invoice locally")
    }
}
