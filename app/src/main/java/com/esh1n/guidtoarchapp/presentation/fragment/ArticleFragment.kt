package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.adapter.ArticleContentAdapter
import com.esh1n.guidtoarchapp.presentation.viewmodel.ArticleViewModel

class ArticleFragment : Fragment(R.layout.fragment_acticle) {

    private lateinit var viewModel: ArticleViewModel

    private lateinit var adapter: ArticleContentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = ArticleContentAdapter(requireActivity())
//        val dividerItemDecoration =
//            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.getArticleItems(getArticleId()).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setArticleItems(it)
            }
        })
    }


    private fun getArticleId(): String {
        return arguments?.getString(ARG_ID) ?: ""
    }

    companion object {
        private const val ARG_ID = "ARG_ID"

        fun newInstance(id: String): ArticleFragment {
            return ArticleFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_ID, id)
                    }
                }
        }
    }
}