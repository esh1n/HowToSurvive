package com.esh1n.guidtoarchapp.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esh1n.guidtoarchapp.data.CategoryDao
import com.esh1n.guidtoarchapp.data.CategoryEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class CategoriesRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<CategoryEntry>> = categoryDao.getAllCategories()

    @WorkerThread
    suspend fun insert(word: CategoryEntry) {
        val unit = categoryDao.insert(word)
        return unit;
    }

    @ExperimentalCoroutinesApi
    fun queryCategories(search: String): Flow<List<CategoryEntry>> {
        return categoryDao.queryCategories(search) //Get searched dogs from Room Database
            //Combine the result with another flow
//            .combine(topBreedsFlow) { dogs, topDogs ->
//                dogs.applyToDog(topDogs)
//            }
            .flowOn(Dispatchers.Default)
            //Return the latest values
            .conflate()
    }
}