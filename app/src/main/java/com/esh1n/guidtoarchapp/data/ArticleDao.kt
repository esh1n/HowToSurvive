package com.esh1n.guidtoarchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDao {
    @Query("SELECT * from article_table WHERE categoryName =:categoryName ORDER BY name ASC")
    abstract fun getArticlesByCategory(categoryName: String): LiveData<List<ArticleEntry>>

    @Query("SELECT * from article_table WHERE name=:id")
    abstract fun getArticleById(id: String): Flow<ArticleEntry>

    @Query("SELECT * from article_table WHERE isSaved = 1")
    abstract fun getSavedArticles(): LiveData<List<ArticleEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(articleEntry: List<ArticleEntry>)

    @Query("DELETE FROM article_table")
    abstract suspend fun deleteAll()

    @Query("UPDATE article_table SET isSaved = :updateValue WHERE name=:id")
    abstract suspend fun markAsSaved(id: String, updateValue: Int)
}