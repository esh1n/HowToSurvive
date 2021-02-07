package com.esh1n.guidtoarchapp.presentation

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


class CategoriesAdapter internal constructor(
    context: Context,
    private val clickOnItem: (CategoryEntry) -> (Unit)
) : RecyclerView.Adapter<CategoriesAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<CategoryEntry>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
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
            words.getOrNull(bindingAdapterPosition)?.let {
                clickOnItem(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.item_category, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.populate(current)
    }

    internal fun setCategories(words: List<CategoryEntry>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}
