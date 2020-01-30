package com.esh1n.guidtoarchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [CategoryEntry::class, ArticleEntry::class], version = 2)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): CategoryDao
    abstract fun articlesDao(): ArticleDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.wordDao(), database.articlesDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: CategoryDao, articleDao: ArticleDao) {
            wordDao.deleteAll()

            val categories = arrayListOf(
                CategoryEntry("Медицина", "medicine"),
                CategoryEntry("Быт", "human"),
                CategoryEntry("Терроризм", "security")
            )
            val articles = arrayListOf(
                ArticleEntry("Yury pechen is avlive", "Печенка умерла", "Медицина"),
                ArticleEntry("Yulik orders Devilery", "Вкусный бургер", "Медицина"),
                ArticleEntry("Yury pechen is avlive", "Печенка умерла", "Быт"),
                ArticleEntry("Наполеон", "Вкусный бургер", "Быт"),
                ArticleEntry("live", "В санкт петербурге ", "Терроризм"),
                ArticleEntry("ПРофессор в питере", "Олег Соколов", "Терроризм")
            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
