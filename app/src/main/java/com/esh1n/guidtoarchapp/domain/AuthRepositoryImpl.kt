package com.esh1n.guidtoarchapp.domain

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Simple repository for holding "hasAuthData" flag
 */
class AuthRepositoryImpl @Inject constructor(private val sharedPrefs: SharedPreferences) :
    IAuthRepo {

    companion object {
        private const val PREFS_HAS_AUTH_DATA = "has_auth_data"
    }

    override val hasAuth: Boolean
        get() = sharedPrefs.getBoolean(PREFS_HAS_AUTH_DATA, false)

    override fun updateAuth(isAuth: Boolean) {
        sharedPrefs.edit()
            .putBoolean(PREFS_HAS_AUTH_DATA, isAuth)
            .apply()
    }
}