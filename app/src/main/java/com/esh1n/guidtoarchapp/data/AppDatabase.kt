package com.esh1n.guidtoarchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [CategoryEntry::class, ArticleEntry::class], version = 3)
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
                ArticleEntry(
                    "Медицина1",
                    "{text:Медицина11}{image:ic_phone}{text:Медицина12}",
                    "Медицина"
                ),
                ArticleEntry(
                    "Медицина2",
                    "{text:Медицина21}{image:ic_compass}{text:Медицина}",
                    "Медицина"
                ),
                ArticleEntry("Быт1", "{text:Быт11}{image:ic_compass}{text:Быт12}", "Быт"),
                ArticleEntry("Быт2", "{text:Быт21}{image:ic_phone}{text:Быт22}", "Быт"),
                ArticleEntry("Терроризм1", "{text:Text1}{image:ic_phone}{text:Text1}", "Терроризм"),
                ArticleEntry(
                    "Терроризм2",
                    "{text:Text1}{image:ic_compass}{text:Text1}",
                    "Терроризм"
                )
            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
