package com.esh1n.guidtoarchapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.esh1n.guidtoarchapp.R

class ArticlesByCategoryFragment : Fragment(R.layout.fragment_articles) {

    private var tvArticles: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvArticles = view.findViewById(R.id.tv_articles)
        tvArticles?.text = getArticles()
    }

    private fun getArticles(): String {
        return arguments?.getString(ARG, "") ?: ""
    }

    companion object {
        const val ARG = "articles"

    }

}