package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.Word
import com.esh1n.guidtoarchapp.data.WordDao

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}