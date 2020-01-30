package com.esh1n.guidtoarchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class ArticleDao {
    @Query("SELECT * from article_table WHERE categoryName =:categoryName ORDER BY name ASC")
    abstract fun getArticlesByCategory(categoryName: String): LiveData<List<ArticleEntry>>

    @Query("SELECT * from article_table WHERE name=:id")
    abstract fun getArticleById(id: String): LiveData<ArticleEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(articleEntry: List<ArticleEntry>)

    @Query("DELETE FROM article_table")
    abstract suspend fun deleteAll()
}