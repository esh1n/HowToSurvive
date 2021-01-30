package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.ArticlesRepository
import com.esh1n.guidtoarchapp.domain.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArticlesByCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoriesRepository
    private val articlesRepository: ArticlesRepository

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        val wordsDao = database.wordDao()
        val artcilesDao = database.articlesDao()
        repository = CategoriesRepository(wordsDao)
        articlesRepository = ArticlesRepository(artcilesDao)
    }

    fun getArticlesByCategory(catName: String): LiveData<List<ArticleEntry>> {
        return liveData() {
            emitSource(articlesRepository.articlesByCategories(catName))
        }
    }

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}