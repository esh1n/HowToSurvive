package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI

class SavedArticlesVM(application: Application) : AndroidViewModel(application) {

    private val articlesRepository = GlobalDI.getArticlesRepository()

    fun getSavedArticles(): LiveData<List<ArticleEntry>> {
        return liveData() {
            emitSource(articlesRepository.savedArticles())
        }
    }
}