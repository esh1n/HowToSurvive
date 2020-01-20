package com.esh1n.guidtoarchapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.esh1n.guidtoarchapp.R

class ArticleFragment : Fragment(R.layout.fragment_acticle) {

    private var tvTitle: TextView? = null
    private var tvContent: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle = view.findViewById(R.id.tv_headline)
        tvContent = view.findViewById(R.id.tv_content)
        val title = getTitle()
        val content = getContent()
        tvTitle?.text = title
        tvContent?.text = content
    }

    private fun getTitle(): String {
        return arguments?.getString(ARG_TITLE) ?: ""
    }

    private fun getContent(): String {
        return arguments?.getString(ARG_CONTENT) ?: ""
    }

    companion object {
        const val ARG_TITLE = "title"
        const val ARG_CONTENT = "content"

        fun newInstance(title: String, content: String): ArticleFragment {
            return ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_CONTENT, content)
                }
            }
        }
    }
}