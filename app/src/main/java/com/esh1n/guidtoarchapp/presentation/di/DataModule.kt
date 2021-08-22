package com.esh1n.guidtoarchapp.presentation.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.ArticleDao
import com.esh1n.guidtoarchapp.data.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val DEFAULT_PREFERENCES = "hts_preferences"

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "HTS_Database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articlesDao()
    }

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }


}