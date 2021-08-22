package com.esh1n.guidtoarchapp.presentation.articles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.domain.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedArticlesVM @Inject constructor(private val articlesRepository: ArticlesRepository) :
    ViewModel() {

    fun getSavedArticles(): LiveData<List<ArticleEntry>> {
        return liveData {
            emitSource(articlesRepository.savedArticles())
        }
    }
}