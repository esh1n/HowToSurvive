package com.esh1n.guidtoarchapp.presentation.articles.viewmodel

import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.data.ArticleEntry
import com.esh1n.guidtoarchapp.presentation.articles.adapter.*
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import com.esh1n.guidtoarchapp.presentation.mvibase.BaseViewModel
import com.esh1n.guidtoarchapp.presentation.mvibase.UiEffect
import com.esh1n.guidtoarchapp.presentation.mvibase.UiState
import com.esh1n.guidtoarchapp.presentation.mvibase.UiWish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern


class ArticleViewModel : BaseViewModel<Wish, State, Effect>() {

    private val articlesRepository = GlobalDI.getArticlesRepository()

    @SuppressWarnings
    private fun mapArticleToBaseModel(articleEntry: ArticleEntry): List<UiArticlePart> {
        val items = arrayListOf<UiArticlePart>(TitleModel(articleEntry.name, articleEntry.isSaved))
        val m: Matcher = Pattern.compile("\\{([^}]+)\\}").matcher(articleEntry.content)
        while (m.find()) {
            val item = mapContentToBaseModel(m.group(1))
            items.add(item)

        }
        return items
    }

    private fun mapContentToBaseModel(value: String = "{text:Медицина11}{image:ic_phone}{text:Медицина12}"): UiArticlePart {
        val type = value.substringBefore(":")
        val content = value.substringAfter(":")
        return when (type) {
            "textBold" -> return TextBoldModel(content)
            "text" -> return TextModel(content)
            "image" -> return ImageModel(content)
            "link" -> return LinkModel(content)
            else -> TextModel(content)
        }
    }


    private fun toggleSavedState(saved: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        currentState.articleId?.let { id ->
            articlesRepository.markAsSaved(id, saved)
            setEffect { Effect.ShowArticleStateChangedToast(saved) }
        }
    }

    private fun loadArticle(id: String) {
        viewModelScope.launch {
            articlesRepository.getArticleById(id)
                .map(::mapArticleToBaseModel)
                .flowOn(Dispatchers.IO)
                .collect { setState { copy(articleId = id, articleParts = it) } }
        }
    }

    override fun createInitialState() = State(null, emptyList())

    override fun handleEvent(wish: Wish) {
        when (wish) {
            is Wish.OnOpenLink -> setEffect { Effect.OnOpenLink(wish.link) }
            is Wish.OnToggleSaved -> toggleSavedState(wish.saved)
            is Wish.OnLoadArticle -> loadArticle(wish.id)
        }
    }
}

sealed class Wish : UiWish {
    data class OnToggleSaved(val saved: Boolean) : Wish()
    data class OnOpenLink(val link: String) : Wish()
    data class OnLoadArticle(val id: String) : Wish()
}

data class State(val articleId: String?, val articleParts: List<UiArticlePart>) : UiState

sealed class Effect : UiEffect {
    data class ShowArticleStateChangedToast(val saved: Boolean) : Effect()
    data class OnOpenLink(val link: String) : Effect()
}
//sealed class RandomNumberState {
//    object Idle : RandomNumberState()
//    object Loading : RandomNumberState()
//    data class Categories(val number : Int) : RandomNumberState()
//}