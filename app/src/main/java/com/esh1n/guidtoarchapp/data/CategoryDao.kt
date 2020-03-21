package com.esh1n.guidtoarchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class CategoryDao {

    @Query("SELECT * from category_table ORDER BY name ASC")
    abstract fun getAllCategories(): LiveData<List<CategoryEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(word: CategoryEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(categories: List<CategoryEntry>)

    @Query("DELETE FROM category_table")
    abstract suspend fun deleteAll()
}