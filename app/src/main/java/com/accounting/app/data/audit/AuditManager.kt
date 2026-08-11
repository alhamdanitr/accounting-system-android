package com.accounting.app.data.audit

import android.content.Context
import android.util.Log

data class LocalAuditLog(
    val action: String,
    val entity: String,
    val details: String,
    val timestamp: Long = System.currentTimeMillis()
)

class AuditManager(private val context: Context) {
    private val TAG = "AuditManager"

    fun logLocalAction(action: String, entity: String, details: String) {
        val log = LocalAuditLog(action, entity, details)
        Log.d(TAG, "Audit Log Recorded: [${log.action}] on ${log.entity} - ${log.details} at ${log.timestamp}")
        // Here we can store locally in Room DB and queue for sync
    }
}
