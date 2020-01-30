package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.CategoryDao
import com.esh1n.guidtoarchapp.data.CategoryEntry

class WordRepository(private val wordDao: CategoryDao) {
    val allWords: LiveData<List<CategoryEntry>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: CategoryEntry) {
        wordDao.insert(word)
    }
}