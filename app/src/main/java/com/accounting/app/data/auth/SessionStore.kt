package com.accounting.app.data.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/** Persistent authenticated session. Tokens are never stored in plain SharedPreferences. */
class SessionStore(context: Context) {
    private val preferences = EncryptedSharedPreferences.create(
        "accounting_secure_session",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context.applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    var accessToken: String?
        get() = preferences.getString(KEY_ACCESS_TOKEN, null)
        private set(value) = preferences.edit().putString(KEY_ACCESS_TOKEN, value).apply()

    var refreshToken: String?
        get() = preferences.getString(KEY_REFRESH_TOKEN, null)
        private set(value) = preferences.edit().putString(KEY_REFRESH_TOKEN, value).apply()

    val tenantId: String?
        get() = preferences.getString(KEY_TENANT_ID, null)

    val userId: String?
        get() = preferences.getString(KEY_USER_ID, null)

    val deviceId: String?
        get() = preferences.getString(KEY_DEVICE_ID, null)

    val branchId: String?
        get() = preferences.getString(KEY_BRANCH_ID, null)

    var syncCursor: String
        get() = preferences.getString(KEY_SYNC_CURSOR, "0") ?: "0"
        private set(value) = preferences.edit().putString(KEY_SYNC_CURSOR, value).apply()

    fun saveSession(session: AuthSession) {
        preferences.edit()
            .putString(KEY_ACCESS_TOKEN, session.accessToken)
            .putString(KEY_REFRESH_TOKEN, session.refreshToken)
            .putString(KEY_TENANT_ID, session.tenantId)
            .putString(KEY_USER_ID, session.userId)
            .putString(KEY_DEVICE_ID, session.deviceId)
            .putString(KEY_BRANCH_ID, session.branchId)
            .apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun updateAccessToken(token: String) {
        accessToken = token
    }

    fun updateSyncCursor(cursor: String) {
        syncCursor = cursor
    }

    fun hasSession(): Boolean = !accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank() && !tenantId.isNullOrBlank() && !deviceId.isNullOrBlank()

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_TENANT_ID = "tenant_id"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_DEVICE_ID = "device_id"
        private const val KEY_BRANCH_ID = "branch_id"
        private const val KEY_SYNC_CURSOR = "sync_cursor"
    }
}

data class AuthSession(
    val accessToken: String,
    val refreshToken: String,
    val tenantId: String,
    val userId: String,
    val deviceId: String,
    val branchId: String?,
)
