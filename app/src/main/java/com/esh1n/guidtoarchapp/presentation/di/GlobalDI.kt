package com.esh1n.guidtoarchapp.presentation.di

import android.content.Context
import android.content.SharedPreferences
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.PreferenceRepository
import com.esh1n.guidtoarchapp.domain.ArticlesRepository
import com.esh1n.guidtoarchapp.domain.AuthRepository
import com.esh1n.guidtoarchapp.domain.CategoriesRepository


/**
 * Singleton for "DI" in our example app
 */
object GlobalDI {

    private lateinit var applicationContext: Context

    private lateinit var database: AppDatabase

    private lateinit var prefs: SharedPreferences

    fun initWithContext(applicationContext: Context) {
        GlobalDI.applicationContext = applicationContext
        database = AppDatabase.getDatabase(applicationContext)
        prefs = applicationContext.getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getAuthRepository(): AuthRepository {
        return AuthRepository(prefs)
    }

    fun getArticlesRepository(): ArticlesRepository {
        return ArticlesRepository(database.articlesDao())
    }

    fun getCategoriesRepository(): CategoriesRepository {
        return CategoriesRepository(database.categoryDao())
    }

    fun getPreferenceRepository() = PreferenceRepository(prefs)

    private const val DEFAULT_PREFERENCES = "myapp_preferences"
}