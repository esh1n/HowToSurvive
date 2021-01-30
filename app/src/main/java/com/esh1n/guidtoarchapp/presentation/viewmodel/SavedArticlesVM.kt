package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.domain.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedArticlesVM(application: Application) : AndroidViewModel(application) {

    private val articlesRepository: ArticlesRepository

    init {
        val articlesDao = AppDatabase.getDatabase(application).articlesDao()
        articlesRepository = ArticlesRepository(articlesDao)
    }

    fun getSavedArticles(): LiveData<List<ArticleEntry>> {
        return liveData() {
            emitSource(articlesRepository.savedArticles())
        }
    }

    fun markAsSaved(id: String, isSaved: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        articlesRepository.markAsSaved(id, isSaved)
    }
}