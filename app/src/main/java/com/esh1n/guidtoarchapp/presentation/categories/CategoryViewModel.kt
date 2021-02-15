package com.esh1n.guidtoarchapp.presentation.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoriesRepository = GlobalDI.getCategoriesRepository()

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