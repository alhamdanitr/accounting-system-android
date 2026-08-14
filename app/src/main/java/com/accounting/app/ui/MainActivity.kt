package com.accounting.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.accounting.app.data.sync.SyncWorker
import java.util.concurrent.TimeUnit
import com.accounting.app.data.remote.NetworkModule
import com.accounting.app.ui.screens.LoginScreen
import com.accounting.app.ui.screens.MainScreen
import com.accounting.app.ui.theme.EnterpriseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkModule.initialize(applicationContext)
        scheduleBackgroundSync()
        setContent {
            var authenticated by remember { mutableStateOf(NetworkModule.sessionStore.hasSession()) }
            EnterpriseTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (authenticated) {
                        MainScreen()
                    } else {
                        LoginScreen(onAuthenticated = { authenticated = true })
                    }
                }
            }
        }
    }

    private fun scheduleBackgroundSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "accounting-background-sync",
            ExistingPeriodicWorkPolicy.UPDATE,
            request,
        )
    }
}
