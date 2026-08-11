package com.accounting.app.data.notifications

import android.content.Context
import android.util.Log

class NotificationManager(private val context: Context) {
    private val TAG = "NotificationManager"

    fun showLocalAlert(title: String, message: String) {
        Log.d(TAG, "Showing local notification alert: [$title] $message")
        // Trigger Android NotificationCompat
    }
}
