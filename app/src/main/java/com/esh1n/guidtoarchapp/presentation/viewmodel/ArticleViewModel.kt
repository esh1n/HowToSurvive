package com.esh1n.guidtoarchapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.presentation.adapter.*
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern


class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articlesRepository = GlobalDI.getArticlesRepository()
    private lateinit var articleId: String

    private fun getArticlesById(id: String): LiveData<ArticleEntry> {
        articleId = id
        return liveData() {
            emitSource(articlesRepository.getArticleById(id))
        }
    }

    fun getArticleItems(id: String): LiveData<List<BaseModel>> {
        return Transformations.map(getArticlesById(id)) { article -> mapArticleToBaseModel(article) }
    }

    private fun mapArticleToBaseModel(articleEntry: ArticleEntry): List<BaseModel> {
        val items = arrayListOf<BaseModel>(TitleModel(articleEntry.name, articleEntry.isSaved))
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
            "textBold" -> return TextBoldModel(content)
            "text" -> return TextModel(content)
            "image" -> return ImageModel(content)
        }
        return null
    }


    fun toogleSavedState(checked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        articlesRepository.markAsSaved(articleId, checked)
    }
}