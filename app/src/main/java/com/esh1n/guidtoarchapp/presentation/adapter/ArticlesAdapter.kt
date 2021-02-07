package com.esh1n.guidtoarchapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R


class ArticlesAdapter internal constructor(
    context: Context, private val clickOnItem: (String) -> (Unit)
) : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var articleDescriptions = emptyList<String>() // Cached copy of words

    inner class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_article)

        init {
            itemView.setOnClickListener(this)
        }

        fun populate(article: String) {
            titleTextView.text = article
        }

        override fun onClick(v: View?) {
            val category = articleDescriptions[bindingAdapterPosition]
            clickOnItem(category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val itemView = inflater.inflate(R.layout.item_article, parent, false)
        return ArticlesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val current = articleDescriptions[position]
        holder.populate(current)
    }

    internal fun setArticles(artciles: List<String>) {
        this.articleDescriptions = artciles
        notifyDataSetChanged()
    }

    override fun getItemCount() = articleDescriptions.size
}
