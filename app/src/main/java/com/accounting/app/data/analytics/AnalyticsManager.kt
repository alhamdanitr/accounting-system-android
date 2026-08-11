package com.accounting.app.data.analytics

import android.content.Context
import android.util.Log

data class SalesChartPoint(
    val date: String,
    val salesAmount: Double,
    val profitAmount: Double
)

class AnalyticsManager(private val context: Context) {
    private val TAG = "AnalyticsManager"

    fun calculateLocalMetrics(): Map<String, Any> {
        Log.d(TAG, "Calculating local analytics metrics for dashboard...")
        return mapOf(
            "totalSales" to 15400.0,
            "totalExpenses" to 1200.0,
            "netProfit" to 4200.0,
            "itemsInStock" to 340
        )
    }
}
