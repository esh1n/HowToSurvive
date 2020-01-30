package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.adapter.ArticlesAdapter
import com.esh1n.guidtoarchapp.presentation.utils.addFragmentToStack
import com.esh1n.guidtoarchapp.presentation.viewmodel.ArticlesByCategoryViewModel

class ArticlesByCategoryFragment : Fragment(R.layout.fragment_articles) {


    private lateinit var adapter: ArticlesAdapter

    private lateinit var viewModel: ArticlesByCategoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "onViewCreated")
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = ArticlesAdapter(requireActivity(), this::openArticleById)
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        //adapter.setArticles(getCategoryId())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("Lifecycle", "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArticlesByCategoryViewModel::class.java)
        observeData()
    }

    private fun observeData() {
        viewModel.getArticlesByCategory(getCategoryId())
            .observe(viewLifecycleOwner, Observer { articles ->
                // Update the cached copy of the words in the adapter.
                articles?.let {
                    val articleStrings = articles.map { it.name }
                    adapter.setArticles(articleStrings)
                }
            })
    }

    private fun openArticleById(id: String) {
        fragmentManager.addFragmentToStack(ArticleFragment.newInstance(id, "Sofya"))
    }

    private fun getCategoryId(): String {
        return arguments?.getString(ARG) ?: ""
    }

    companion object {
        const val ARG = "catId"

        fun newInstance(catId: String): ArticlesByCategoryFragment {
            return ArticlesByCategoryFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG, catId)
                    }
                }
        }
    }

}