package com.esh1n.guidtoarchapp.presentation.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.RootActivity
import com.esh1n.guidtoarchapp.presentation.articles.adapter.ArticlesAdapter
import com.esh1n.guidtoarchapp.presentation.articles.viewmodel.ArticlesByCategoryViewModel
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import com.esh1n.guidtoarchapp.presentation.utils.observeNonNull
import com.esh1n.guidtoarchapp.presentation.utils.setABTitle
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticlesByCategoryFragment : Fragment(R.layout.fragment_articles) {

    private val viewModel: ArticlesByCategoryViewModel by viewModels()

    private val args: ArticlesByCategoryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sharedElementEnterTransition = MaterialContainerTransform().apply {
//            drawingViewId = R.id.activity_root__fragment__nav_host
//            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
//            scrimColor = Color.TRANSPARENT
//            setAllContainerColors(requireContext().getColorFromAttribute(R.attr.colorSurface))
//        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view.findViewById<RecyclerView>(R.id.recyclerview)) {
            adapter = ArticlesAdapter(requireActivity(), ::openArticleById)
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(requireActivity())
        }
        val title = getString(R.string.text_artciles_by_title, args.categoryId)
        (requireActivity() as RootActivity).setABTitle(title)
    }

    override fun onStart() {
        super.onStart()
        observeData()
    }

    private fun observeData() {
        viewModel.getArticlesByCategory(args.categoryId)
            .observeNonNull(viewLifecycleOwner) { articles ->
                recyclerview.adapter<ArticlesAdapter>().setArticles(articles.map { it.name })
            }
    }

    private fun openArticleById(id: String) {
        findNavController().navigate(
            R.id.action__ArticlesByCategoryFragment__to__ArticleFragment,
            ArticleFragmentArgs(articleId = id).toBundle()
        )
    }
}