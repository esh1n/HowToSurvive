package com.esh1n.guidtoarchapp.presentation.articles


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.RootActivity
import com.esh1n.guidtoarchapp.presentation.articles.adapter.ArticlesAdapter
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.SavedArticlesVM
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import com.esh1n.guidtoarchapp.presentation.utils.observeNonNull
import com.esh1n.guidtoarchapp.presentation.utils.setABTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_articles.recyclerview
import kotlinx.android.synthetic.main.fragment_categories.*

@AndroidEntryPoint
class SavedArticlesFragment : Fragment(R.layout.fragment_articles) {

    private lateinit var viewModel: SavedArticlesVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerview) {
            adapter =
                ArticlesAdapter(requireActivity(), this@SavedArticlesFragment::openArticleById)
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(requireActivity())
        }
        viewModel = ViewModelProvider(this).get(SavedArticlesVM::class.java)
        observeData()
        (requireActivity() as RootActivity).setABTitle(getString(R.string.favorite_articles))
    }

    private fun observeData() {
        viewModel.getSavedArticles()
            .observeNonNull(viewLifecycleOwner, { articles ->
                recyclerview.adapter<ArticlesAdapter>().setArticles(articles.map { it.name })
            })
    }

    private fun openArticleById(id: String) {
        findNavController().navigate(
            R.id.action__FavoritesContainerFragment__to__ArticleFragment,
            ArticleFragmentArgs(articleId = id).toBundle()
        )
    }
}
