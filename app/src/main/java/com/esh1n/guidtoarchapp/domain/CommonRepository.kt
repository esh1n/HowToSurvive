package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.ArticleDao
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.data.CategoryDao
import com.esh1n.guidtoarchapp.data.CategoryEntry

class CommonRepository(private val wordDao: CategoryDao, private val artcilesDao: ArticleDao) {

    val allWords: LiveData<List<CategoryEntry>> = wordDao.getAllWords()

    fun articlesByCategories(categoryName: String): LiveData<List<ArticleEntry>> =
        artcilesDao.getArticlesByCategory(categoryName)

    fun getArticleById(id: String): LiveData<ArticleEntry> {
        return artcilesDao.getArticleById(id)
    }

    @WorkerThread
    suspend fun insert(word: CategoryEntry) {
        wordDao.insert(word)
    }
}