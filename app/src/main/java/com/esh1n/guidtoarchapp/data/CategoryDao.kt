package com.esh1n.guidtoarchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class CategoryDao {

    @Query("SELECT * from category_table ORDER BY name ASC")
    abstract fun getAllWords(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(word: Category)

    @Query("DELETE FROM category_table")
    abstract suspend fun deleteAll()
}