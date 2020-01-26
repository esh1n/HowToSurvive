package com.esh1n.guidtoarchapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils


class ArticleContentAdapter constructor(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var articleModels = emptyList<BaseModel>()

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)

        fun populate(article: TitleModel) {
            titleTextView.text = article.value
        }

    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_text)

        fun populate(article: TextModel) {
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
        return when (articleModels[position]) {
            is TitleModel -> {
                ITEM_TITLE
            }
            is TextModel -> {
                ITEM_TEXT
            }
            else -> {
                ITEM_IMAGE
            }
        }
    }

    override fun getItemCount() = articleModels.size

    companion object {
        const val ITEM_TITLE = 1
        const val ITEM_TEXT = 2
        const val ITEM_IMAGE = 3
    }
}

sealed class BaseModel(val value: String)
class TitleModel(value: String) : BaseModel(value)
class TextModel(value: String) : BaseModel(value)
class ImageModel(imagePath: String) : BaseModel(imagePath)