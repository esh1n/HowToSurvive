package com.esh1n.guidtoarchapp.presentation.articles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.ArticlesRepository
import com.esh1n.guidtoarchapp.domain.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesByCategoryViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val articlesRepository: ArticlesRepository
) : ViewModel() {


    fun getArticlesByCategory(catName: String): LiveData<List<ArticleEntry>> {
        return liveData {
            emitSource(articlesRepository.articlesByCategories(catName))
        }
    }

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        categoriesRepository.insert(word)
    }
}