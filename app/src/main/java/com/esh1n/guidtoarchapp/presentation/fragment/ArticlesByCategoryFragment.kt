package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.adapter.ArticlesAdapter
import com.esh1n.guidtoarchapp.presentation.utils.addFragmentToStack

class ArticlesByCategoryFragment : Fragment(R.layout.fragment_articles) {


    private lateinit var adapter: ArticlesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = ArticlesAdapter(requireActivity(), this::openArticleById)
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter.setArticles(getArticles())
    }

    private fun openArticleById(id: String) {
        fragmentManager.addFragmentToStack(ArticleFragment.newInstance(id, "Sofya"))
    }

    private fun getArticles(): List<String> {
        return arguments?.getStringArrayList(ARG) ?: emptyList()
    }

    companion object {
        const val ARG = "articles"

        fun newInstance(articles: ArrayList<String>): ArticlesByCategoryFragment {
            return ArticlesByCategoryFragment()
                .apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG, articles)
                }
            }
        }
    }

}