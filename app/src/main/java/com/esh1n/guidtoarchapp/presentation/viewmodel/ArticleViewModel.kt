package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.esh1n.guidtoarchapp.data.AppDatabase
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.domain.CommonRepository
import com.esh1n.guidtoarchapp.presentation.adapter.BaseModel
import com.esh1n.guidtoarchapp.presentation.adapter.ImageModel
import com.esh1n.guidtoarchapp.presentation.adapter.TextModel
import com.esh1n.guidtoarchapp.presentation.adapter.TitleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern


class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CommonRepository

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).wordDao()
        val artcilesDao = AppDatabase.getDatabase(application, viewModelScope).articlesDao()
        repository = CommonRepository(wordsDao, artcilesDao)
    }

    private fun getArticlesById(id: String): LiveData<ArticleEntry> {
        return liveData() {
            emitSource(repository.getArticleById(id))
        }
    }

    fun getArticleItems(id: String): LiveData<List<BaseModel>> {
        return Transformations.map(getArticlesById(id)) { article -> mapArticleToBaseModel(article) }
    }

    private fun mapArticleToBaseModel(articleEntry: ArticleEntry): List<BaseModel> {
        val items = arrayListOf<BaseModel>(TitleModel(articleEntry.name))
        val m: Matcher = Pattern.compile("\\{([^}]+)\\}").matcher(articleEntry.content)
        while (m.find()) {
            val item = mapContentToBaseModel(m.group(1))
            if (item != null) {
                items.add(item)
            }

        }
        return items
    }

    private fun mapContentToBaseModel(value: String = "{text:Медицина11}{image:ic_phone}{text:Медицина12}"): BaseModel? {
        val type = value.substringBefore(":")
        val content = value.substringAfter(":")
        when (type) {
            "text" -> return TextModel(content)
            "image" -> return ImageModel(content)
        }
        return null
    }

    fun insert(word: CategoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}