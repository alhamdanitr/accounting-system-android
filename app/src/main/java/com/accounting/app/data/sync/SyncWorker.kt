package com.accounting.app.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.accounting.app.data.SyncManager
import com.accounting.app.data.SyncResult

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            when (SyncManager(applicationContext).performSync()) {
                SyncResult.Success -> Result.success()
                SyncResult.NotAuthenticated -> Result.failure()
                SyncResult.NetworkUnavailable, SyncResult.PartialFailure -> Result.retry()
            }
        } catch (error: Exception) {
            Log.e(TAG, "Background sync failed", error)
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "SyncWorker"
    }
}
