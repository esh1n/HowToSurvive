package com.esh1n.guidtoarchapp.presentation.categories

import androidx.lifecycle.ViewModel
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoriesRepository: CategoriesRepository) :
    ViewModel() {

    private val queryFlow = MutableStateFlow("")

    private val _categoriesFlow = queryFlow
        .debounce(300)
        .map { query -> return@map if (query.isEmpty()) "" else query }
        .distinctUntilChanged()
        .flatMapLatest { query -> categoriesRepository.queryCategories(query) }
        .flowOn(Dispatchers.IO)

    val uiState: Flow<List<CategoryEntry>>
        get() = _categoriesFlow

    fun setSearchQuery(query: String) {
        queryFlow.value = query
    }
}