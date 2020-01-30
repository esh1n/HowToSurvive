package com.esh1n.guidtoarchapp.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.Category
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils


class CategoriesAdapter internal constructor(
    context: Context,
    private val clickOnItem: (Category) -> (Unit)
) : RecyclerView.Adapter<CategoriesAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Category>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val categoryImageView: ImageView = itemView.findViewById(R.id.iv_category_logo)

        init {
            itemView.setOnClickListener(this)
        }

        fun populate(word: Category) {
            titleTextView.text = word.name
            val categoryImageSource = UiUtils.getCategoryImage(itemView.context, word.iconId)
            categoryImageView.setImageResource(categoryImageSource)
        }

        override fun onClick(v: View?) {
            val category = words[adapterPosition]
            clickOnItem(category)
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

    internal fun setWords(words: List<Category>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}
