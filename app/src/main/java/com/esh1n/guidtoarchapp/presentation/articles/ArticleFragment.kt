package com.esh1n.guidtoarchapp.presentation.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.articles.adapter.ArticleContentAdapter
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.ArticleViewModel
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.Effect
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.Wish
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import com.esh1n.guidtoarchapp.presentation.utils.openBrowser
import com.esh1n.guidtoarchapp.presentation.utils.showToast
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.flow.collect

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.passWish(Wish.OnLoadArticle(args.articleId))
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                recyclerview.adapter<ArticleContentAdapter>().setArticleItems(it.articleParts)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is Effect.OnOpenLink -> requireActivity().openBrowser(it.link)
                    is Effect.ShowArticleStateChangedToast -> {
                        val res = (if (it.saved) R.string.text_added_to_saved
                        else R.string.text_removed_from_saved)
                        res.let(::showToast)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerview) {
            this.adapter = ArticleContentAdapter(
                requireActivity(),
                { viewModel.passWish(Wish.OnToggleSaved(it)) },
                { viewModel.passWish(Wish.OnOpenLink(it)) }
            )
            layoutManager = LinearLayoutManager(requireActivity())
        }
        observeData()
    }
}