package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.ArticleDao
import com.esh1n.guidtoarchapp.data.ArticleEntry

class ArticlesRepository(private val articleDao: ArticleDao) {
    fun savedArticles(): LiveData<List<ArticleEntry>> =
        articleDao.getSavedArticles()

    fun articlesByCategories(categoryName: String): LiveData<List<ArticleEntry>> =
        articleDao.getArticlesByCategory(categoryName)

    fun getArticleById(id: String): LiveData<ArticleEntry> {
        return articleDao.getArticleById(id)
    }

    @WorkerThread
    suspend fun markAsSaved(id: String, isSaved: Boolean) {
        val updateValue = if (isSaved) 1 else 0;
        articleDao.markAsSaved(id, updateValue)
    }
}