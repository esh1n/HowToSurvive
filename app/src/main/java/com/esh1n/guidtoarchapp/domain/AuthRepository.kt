package com.esh1n.guidtoarchapp.domain

import android.content.SharedPreferences

/**
 * Simple repository for holding "hasAuthData" flag
 */
class AuthRepository(private val sharedPrefs: SharedPreferences) {

    companion object {
        private const val PREFS_AUTH_NAME = "auth_prefs"

        private const val PREFS_HAS_AUTH_DATA = "${PREFS_AUTH_NAME}.has_auth_data"
    }

    fun putHasAuthData(hasAuthData: Boolean) {
        sharedPrefs.edit()
            .putBoolean(PREFS_HAS_AUTH_DATA, hasAuthData)
            .apply()
    }

    fun hasAuthData(): Boolean = sharedPrefs.getBoolean(PREFS_HAS_AUTH_DATA, false)

}