package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import com.esh1n.guidtoarchapp.presentation.utils.observeNonNull
import com.esh1n.guidtoarchapp.presentation.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()

    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerview) {
            this.adapter = ArticleContentAdapter(requireActivity(), viewModel::toogleSavedState)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getArticleItems(args.articleId).observeNonNull(viewLifecycleOwner, {
            recyclerview.adapter<ArticleContentAdapter>().setArticleItems(it)
        })
    }

}