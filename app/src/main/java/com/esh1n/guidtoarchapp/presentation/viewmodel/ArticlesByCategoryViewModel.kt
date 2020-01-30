package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.CommonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArticlesByCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CommonRepository

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).wordDao()
        val artcilesDao = AppDatabase.getDatabase(application, viewModelScope).articlesDao()
        repository = CommonRepository(wordsDao, artcilesDao)
    }

    fun getArticlesByCategory(catName: String): LiveData<List<ArticleEntry>> {
        return liveData() {
            emitSource(repository.articlesByCategories(catName))
        }
    }

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}