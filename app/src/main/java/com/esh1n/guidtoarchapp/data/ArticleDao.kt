package com.esh1n.guidtoarchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class ArticleDao {
    @Query("SELECT * from category_table ORDER BY name ASC")
    abstract fun getArticlesByCategory(category: String): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(articleEntry: ArticleEntry)

    @Query("DELETE FROM article_table")
    abstract suspend fun deleteAll()
}