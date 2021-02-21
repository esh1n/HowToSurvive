package com.esh1n.guidtoarchapp.presentation.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils
import kotlin.math.ceil

class CategoriesAdapter internal constructor(
    context: Context,
    private val clickOnItem: (CategoryEntry) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<BaseCategoryItem>()

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val categoryImageView: ImageView = itemView.findViewById(R.id.iv_category_logo)

        init {
            itemView.setOnClickListener(this)
        }

        fun populate(word: CategoryEntry) {
            titleTextView.text = word.name
            val categoryImageSource = UiUtils.getCategoryImage(itemView.context, word.iconId)
            categoryImageView.setImageResource(categoryImageSource)
        }

        override fun onClick(v: View?) {
            (items.getOrNull(bindingAdapterPosition) as? CategoryItem)?.let {
                clickOnItem(it.category)
            }
        }
    }

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(v: View?) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CATEGORY_ITEM_TYPE -> {
                val itemView = inflater.inflate(R.layout.item_category, parent, false)
                CategoryViewHolder(itemView)
            }
            else -> {
                val itemView = inflater.inflate(R.layout.item_banner, parent, false)
                BannerViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = items[position]
        return when (holder) {
            is CategoryViewHolder -> {
                holder.populate((current as CategoryItem).category)
            }
            else -> {
                val bannerHolder = holder as BannerViewHolder
                //bannerHolder
            }
        }

    }

    internal fun setCategories(entries: List<CategoryEntry>) {
        this.items = mutableListOf<BaseCategoryItem>().apply {
            addAll(entries.map { CategoryItem(it) })
            if (entries.isNotEmpty()) {
                add(ceil(entries.size.toDouble() / 2.0).toInt(), BannerItem())
            }

        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is BannerItem -> BANNER_ITEM_TYPE
            is CategoryItem -> CATEGORY_ITEM_TYPE
        }
    }

    companion object {
        const val CATEGORY_ITEM_TYPE = 0
        const val BANNER_ITEM_TYPE = 1
    }
}

sealed class BaseCategoryItem
class CategoryItem(val category: CategoryEntry) : BaseCategoryItem()
class BannerItem : BaseCategoryItem()
