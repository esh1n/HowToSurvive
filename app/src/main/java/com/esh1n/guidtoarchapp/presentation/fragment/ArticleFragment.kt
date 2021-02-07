package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter
import com.esh1n.guidtoarchapp.presentation.viewmodel.ArticleViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()

    private lateinit var adapter: ArticleContentAdapter

    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = ArticleContentAdapter(requireActivity(), viewModel::toogleSavedState)
        with(recyclerView) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getArticleItems(args.articleId).observe(viewLifecycleOwner, {
            it?.let {
                adapter.setArticleItems(it)
            }
        })
    }

}