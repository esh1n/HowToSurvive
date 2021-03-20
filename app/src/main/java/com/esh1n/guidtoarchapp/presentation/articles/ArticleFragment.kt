package com.esh1n.guidtoarchapp.presentation.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.articles.adapter.ArticleContentAdapter
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.ArticleViewModel
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.Effect
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.Wish
import com.esh1n.guidtoarchapp.presentation.utils.*
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.click
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.Decorator
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor.CircleBarDecor
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor.ScrollBarDecor
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.flow.collect

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()

    private val args: ArticleFragmentArgs by navArgs()

    private val circleBarRadius by lazy { toPixelsFromResource(R.dimen.circle_bar_radius) }

    private val scrollBarWidth by lazy { toPixelsFromResource(R.dimen.scroll_bar_width) }

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
        button_next_article.click {
            findNavController().navigate(
                ArticleFragmentDirections.actionArticleFragmentToArticleFragment(
                    "Ожог"
                )
            )
        }
        with(recyclerview) {
            this.adapter = ArticleContentAdapter(
                requireActivity(),
                { viewModel.passWish(Wish.OnToggleSaved(it)) },
                { viewModel.passWish(Wish.OnOpenLink(it)) }
            )
            val color = requireContext()
                .getColorStateListFromRes(R.color.circle_bar_color)?.defaultColor
                ?: getColorFromAttribute(R.attr.colorSecondary)
            val decorator = Decorator.Builder()
                .underlay(CircleBarDecor(circleBarRadius, color))
                .overlay(ScrollBarDecor(scrollBarWidth))
                .build()
            addItemDecoration(decorator)
            layoutManager = LinearLayoutManager(requireActivity())
        }
        observeData()
    }
}