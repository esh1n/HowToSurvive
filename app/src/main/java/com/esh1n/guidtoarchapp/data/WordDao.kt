package com.esh1n.guidtoarchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    abstract fun getAllWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    abstract suspend fun deleteAll()
}