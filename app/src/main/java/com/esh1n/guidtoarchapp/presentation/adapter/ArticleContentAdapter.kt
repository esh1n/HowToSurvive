package com.esh1n.guidtoarchapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter.Companion.ITEM_BOLD_TEXT
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter.Companion.ITEM_IMAGE
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter.Companion.ITEM_TEXT
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter.Companion.ITEM_TITLE
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils
import com.google.android.material.switchmaterial.SwitchMaterial


class ArticleContentAdapter constructor(
    context: Context,
    private val saveListener: (Boolean) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var articleModels = emptyList<BaseModel>()

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val saveArticleSwitch: SwitchMaterial =
            itemView.findViewById(R.id.switch_save_article)

        fun populate(article: TitleModel) {
            titleTextView.text = article.value
            val switchResource =
                if (article.isSaved) R.string.text_remove_from_saved else R.string.text_save_article
            saveArticleSwitch.text = itemView.context.getString(switchResource)
            saveArticleSwitch.isChecked = article.isSaved
            saveArticleSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                saveListener(isChecked)
            }
        }

    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_text)

        fun populate(article: TextModel) {
            //1.найти строку в []
            //2.найти первое и последнее слово
            //3. индекс начала и конца строки
            //4. по индексам выделить жирным
            titleTextView.text = article.value
            val existEven = listOf(1, 2, 3).any { item -> item % 2 == 0 }
        }

    }

    inner class TextBoldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_text)

        fun populate(article: TextBoldModel) {
            titleTextView.text = article.value
        }

    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.iv_image)

        fun populate(pathToImage: ImageModel) {
            val categoryImageSource = UiUtils.getImageByPath(itemView.context, pathToImage.value)
            imageView.setImageResource(categoryImageSource)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TITLE -> {
                val itemView = inflater.inflate(R.layout.item_article_title, parent, false)
                TitleViewHolder(itemView)
            }
            ITEM_TEXT -> {
                val itemView = inflater.inflate(R.layout.item_article_text, parent, false)
                TextViewHolder(itemView)
            }
            ITEM_BOLD_TEXT -> {
                val itemView = inflater.inflate(R.layout.item_bold_text, parent, false)
                TextBoldViewHolder(itemView)
            }
            ITEM_IMAGE -> {
                val itemView = inflater.inflate(R.layout.item_article_image, parent, false)
                ImageViewHolder(itemView)
            }
            else -> {
                val itemView = inflater.inflate(R.layout.item_article_image, parent, false)
                ImageViewHolder(itemView)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val dayWeatherModel = getItem(position) as TitleModel
                holder.populate(dayWeatherModel)
            }
            is TextViewHolder -> {
                val currentWeather = getItem(position) as TextModel
                holder.populate(currentWeather)
            }
            is TextBoldViewHolder -> {
                val currentWeather = getItem(position) as TextBoldModel
                holder.populate(currentWeather)
            }
            else -> {
                val imageHolder = holder as ImageViewHolder
                val currentWeather = getItem(position) as ImageModel
                imageHolder.populate(currentWeather)
            }
        }

    }

    private fun getItem(position: Int): BaseModel? {
        return if (articleModels.isNullOrEmpty() || position == articleModels.size)
            null
        else articleModels[position]
    }

    fun setArticleItems(models: List<BaseModel>) {
        this.articleModels = models
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return articleModels[position].index

    }

    override fun getItemCount() = articleModels.size

    companion object {
        const val ITEM_TITLE = 1
        const val ITEM_TEXT = 2
        const val ITEM_BOLD_TEXT = 3
        const val ITEM_IMAGE = 4
    }
}

sealed class BaseModel(val index: Int, val value: String)
class TitleModel(value: String, val isSaved: Boolean) : BaseModel(ITEM_TITLE, value)
class TextModel(value: String) : BaseModel(ITEM_TEXT, value)
class TextBoldModel(value: String) : BaseModel(ITEM_BOLD_TEXT, value)
class ImageModel(imagePath: String) : BaseModel(ITEM_IMAGE, imagePath)