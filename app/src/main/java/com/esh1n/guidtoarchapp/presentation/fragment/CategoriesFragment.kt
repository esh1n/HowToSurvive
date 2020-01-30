package com.esh1n.guidtoarchapp.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.presentation.CategoriesAdapter
import com.esh1n.guidtoarchapp.presentation.NewWordActivity
import com.esh1n.guidtoarchapp.presentation.adapter.SpaceItemDecoration
import com.esh1n.guidtoarchapp.presentation.utils.addFragmentToStack
import com.esh1n.guidtoarchapp.presentation.viewmodel.CategoryViewModel

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var categoryViewModel: CategoryViewModel

    private var adapter: CategoriesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = CategoriesAdapter(
            requireActivity(),
            this::openArticlesByCategory
        )
        val dividerItemDecoration = SpaceItemDecoration(32)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

//        fab.setOnClickListener {
//            val intent = Intent(requireActivity(), NewWordActivity::class.java)
//            startActivityForResult(intent, newWordActivityRequestCode)
//        }
        observeData()
    }

    private fun observeData() {
        categoryViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter?.setWords(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = CategoryEntry(it.getStringExtra(NewWordActivity.EXTRA_REPLY), "human")
                categoryViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                requireActivity(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openArticlesByCategory(category: CategoryEntry) {
        val categoryId = category.name
        fragmentManager.addFragmentToStack(ArticlesByCategoryFragment.newInstance(categoryId))
    }


    companion object {
        const val newWordActivityRequestCode = 1
        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

}