package com.esh1n.guidtoarchapp.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.Word
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils


class WordListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val categoryImageView:ImageView = itemView.findViewById(R.id.iv_category_logo)

        fun populate(word:Word){
            titleTextView.text = word.word
            val categoryImageSource = UiUtils.getCategoryImage(itemView.context,word.iconId)
            categoryImageView.setImageResource(categoryImageSource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.populate(current)
    }

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}
