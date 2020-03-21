package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.CategoryDao
import com.esh1n.guidtoarchapp.data.CategoryEntry

class CategoriesRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<CategoryEntry>> = categoryDao.getAllCategories()

    @WorkerThread
    suspend fun insert(word: CategoryEntry) {
        categoryDao.insert(word)
    }
}