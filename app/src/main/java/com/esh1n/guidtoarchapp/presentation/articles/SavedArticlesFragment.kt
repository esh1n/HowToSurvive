package com.esh1n.guidtoarchapp.presentation.articles


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.RootActivity
import com.esh1n.guidtoarchapp.presentation.articles.adapter.ArticlesAdapter
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.SavedArticlesVM
import com.esh1n.guidtoarchapp.presentation.utils.setABTitle

class SavedArticlesFragment : Fragment(R.layout.fragment_articles) {

    private lateinit var adapter: ArticlesAdapter

    private lateinit var viewModel: SavedArticlesVM

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
        val title = getString(R.string.favorite_articles)
        (requireActivity() as RootActivity).setABTitle(title)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("Lifecycle", "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SavedArticlesVM::class.java)
        observeData()
    }

    private fun observeData() {
        viewModel.getSavedArticles()
            .observe(viewLifecycleOwner, Observer { articles ->
                // Update the cached copy of the words in the adapter.
                articles?.let {
                    val articleStrings = articles.map { it.name }
                    adapter.setArticles(articleStrings)
                }
            })
    }

    private fun openArticleById(id: String) {
        findNavController().navigate(
            R.id.action__FavoritesContainerFragment__to__ArticleFragment,
            ArticleFragmentArgs(articleId = id).toBundle()
        )
    }
}
