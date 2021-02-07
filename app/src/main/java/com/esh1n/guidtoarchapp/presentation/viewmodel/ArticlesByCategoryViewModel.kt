package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArticlesByCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoriesRepository = GlobalDI.getCategoriesRepository()
    private val articlesRepository = GlobalDI.getArticlesRepository()

    fun getArticlesByCategory(catName: String): LiveData<List<ArticleEntry>> {
        return liveData {
            emitSource(articlesRepository.articlesByCategories(catName))
        }
    }

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        categoriesRepository.insert(word)
    }
}