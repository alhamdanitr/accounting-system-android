package com.accounting.app.data.settings

import android.content.Context
import android.util.Log

class SettingsManager(private val context: Context) {
    private val TAG = "SettingsManager"

    fun saveLocalSetting(key: String, value: String) {
        Log.d(TAG, "Saving local setting: $key = $value")
        // Save to DataStore or SharedPreferences
    }
}
