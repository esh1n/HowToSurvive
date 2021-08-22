package com.esh1n.guidtoarchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CategoryEntry::class, ArticleEntry::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun articlesDao(): ArticleDao
}

