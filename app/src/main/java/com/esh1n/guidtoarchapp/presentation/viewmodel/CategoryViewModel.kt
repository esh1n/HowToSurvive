package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        CategoriesRepository(AppDatabase.getDatabase(application).wordDao())

    private val queryFlow = MutableStateFlow("")

    private val _categoriesFlow = queryFlow
        .debounce(300)
        .map { query -> return@map if (query.isEmpty()) "" else query }
        .distinctUntilChanged()
        .flatMapLatest { query -> repository.queryCategories(query) }
        .flowOn(Dispatchers.Default)

    val uiState: Flow<List<CategoryEntry>>
        get() = _categoriesFlow

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    fun setSearchQuery(query: String) {
        queryFlow.value = query
    }
}