package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.Category
import com.esh1n.guidtoarchapp.data.CategoryDao

class WordRepository(private val wordDao: CategoryDao) {
    val allWords: LiveData<List<Category>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Category) {
        wordDao.insert(word)
    }
}