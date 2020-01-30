package com.esh1n.guidtoarchapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.Category
import com.esh1n.guidtoarchapp.domain.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<Category>>

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = liveData(){
            emitSource(repository.allWords)
        }
    }

    fun insert(word: Category) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}