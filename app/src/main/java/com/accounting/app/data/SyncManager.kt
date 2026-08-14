package com.accounting.app.data

import android.content.Context
import android.util.Log
import com.accounting.app.data.auth.SessionStore
import com.accounting.app.data.local.AppDatabase
import com.accounting.app.data.local.entity.CustomerEntity
import com.accounting.app.data.local.entity.ProductEntity
import com.accounting.app.data.local.entity.RemoteSyncChangeEntity
import com.accounting.app.data.local.entity.SyncOperationEntity
import com.accounting.app.data.remote.NetworkModule
import com.accounting.app.data.remote.SyncOperationDto
import com.accounting.app.data.remote.SyncPushRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/** Offline-first synchronizer. It only removes a local operation after a server ACK. */
class SyncManager(context: Context) {
    private val appContext = context.applicationContext
    private val database = AppDatabase.getInstance(appContext)
    private val sessionStore: SessionStore
        get() = NetworkModule.sessionStore
    private val gson = Gson()

    suspend fun performSync(): SyncResult = withContext(Dispatchers.IO) {
        if (!sessionStore.hasSession()) return@withContext SyncResult.NotAuthenticated
        val tenantId = sessionStore.tenantId ?: return@withContext SyncResult.NotAuthenticated
        val deviceId = sessionStore.deviceId ?: return@withContext SyncResult.NotAuthenticated

        return@withContext try {
            val pushResult = pushLocalOperations(tenantId, deviceId)
            val pullResult = pullRemoteChanges(tenantId, deviceId)
            if (pushResult && pullResult) SyncResult.Success else SyncResult.PartialFailure
        } catch (error: IOException) {
            Log.w(TAG, "Network unavailable; pending operations retained", error)
            SyncResult.NetworkUnavailable
        } catch (error: Exception) {
            Log.e(TAG, "Synchronization failed", error)
            SyncResult.PartialFailure
        }
    }

    suspend fun queueOperation(operation: SyncOperationEntity) {
        database.syncDao().insertOperation(operation)
    }

    private suspend fun pushLocalOperations(tenantId: String, deviceId: String): Boolean {
        val operations = database.syncDao().getPendingOperations(tenantId, deviceId, System.currentTimeMillis())
        if (operations.isEmpty()) return true

        val response = NetworkModule.apiService.pushSyncOperations(
            SyncPushRequest(
                tenantId = tenantId,
                deviceId = deviceId,
                operations = operations.map { SyncOperationDto(it.idempotencyKey, it.entityType, it.entityId, it.operationType, it.payload) },
            ),
        )
        val acknowledged = response.results.filter { it.status == "SYNCED" }.map { it.idempotencyKey }
        if (acknowledged.isNotEmpty()) database.syncDao().deleteAcknowledged(acknowledged)

        response.results.filter { it.status != "SYNCED" }.forEach { result ->
            val operation = operations.firstOrNull { it.idempotencyKey == result.idempotencyKey } ?: return@forEach
            val attempts = operation.attempts + 1
            database.syncDao().updateState(
                idempotencyKey = operation.idempotencyKey,
                status = result.status,
                attempts = attempts,
                lastError = result.errorMessage,
                nextAttemptAt = System.currentTimeMillis() + backoffMillis(attempts),
            )
        }
        return response.success
    }

    private suspend fun pullRemoteChanges(tenantId: String, deviceId: String): Boolean {
        var cursor = sessionStore.syncCursor
        do {
            val response = NetworkModule.apiService.pullSyncOperations(tenantId, deviceId, cursor, 100)
            if (!response.success) return false
            response.operations.forEach { operation -> applyRemoteOperation(operation) }
            if (response.operations.isNotEmpty()) {
                cursor = response.nextCursor
                sessionStore.updateSyncCursor(cursor)
            }
        } while (response.hasMore)
        return true
    }

    private suspend fun applyRemoteOperation(operation: com.accounting.app.data.remote.RemoteSyncOperation) {
        try {
            when (operation.entityType.uppercase()) {
                "PRODUCT" -> database.productDao().insertProducts(listOf(gson.fromJson(operation.payload, ProductEntity::class.java)))
                "CUSTOMER" -> database.customerDao().insertCustomer(gson.fromJson(operation.payload, CustomerEntity::class.java))
                "SALE", "PURCHASE" -> persistRemoteChange(operation, null)
                else -> persistRemoteChange(operation, "Unsupported remote entity")
            }
        } catch (error: Exception) {
            persistRemoteChange(operation, error.message ?: "Remote operation could not be applied")
        }
    }

    private suspend fun persistRemoteChange(
        operation: com.accounting.app.data.remote.RemoteSyncOperation,
        errorMessage: String?,
    ) {
        database.remoteChangeDao().insert(
            RemoteSyncChangeEntity(
                id = operation.id,
                sequence = operation.sequence,
                tenantId = operation.tenantId,
                deviceId = operation.deviceId,
                entityType = operation.entityType,
                entityId = operation.entityId,
                operationType = operation.operationType,
                payload = operation.payload,
                status = if (errorMessage == null) "PENDING" else "CONFLICT",
                errorMessage = errorMessage,
            ),
        )
    }

    private fun backoffMillis(attempt: Int): Long {
        val exponent = attempt.coerceIn(0, 6)
        return (15_000L * (1L shl exponent)).coerceAtMost(15 * 60 * 1000L)
    }

    companion object {
        private const val TAG = "SyncManager"
    }
}

sealed interface SyncResult {
    data object Success : SyncResult
    data object PartialFailure : SyncResult
    data object NetworkUnavailable : SyncResult
    data object NotAuthenticated : SyncResult
}
