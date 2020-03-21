package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoriesRepository
    val allWords: LiveData<List<CategoryEntry>>

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).wordDao()
        val artcileDao = AppDatabase.getDatabase(application, viewModelScope).articlesDao()
        repository = CategoriesRepository(wordsDao)
        allWords = liveData(){
            emitSource(repository.allCategories)
        }
    }

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}