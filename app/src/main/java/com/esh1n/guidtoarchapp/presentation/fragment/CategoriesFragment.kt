package com.esh1n.guidtoarchapp.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.presentation.CategoriesAdapter
import com.esh1n.guidtoarchapp.presentation.MainActivity
import com.esh1n.guidtoarchapp.presentation.NewWordActivity
import com.esh1n.guidtoarchapp.presentation.adapter.SpaceItemDecoration
import com.esh1n.guidtoarchapp.presentation.utils.addFragmentToStack
import com.esh1n.guidtoarchapp.presentation.viewmodel.CategoryViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var viewModel: CategoryViewModel

    private var adapter: CategoriesAdapter? = null

    private var searchView: SearchView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = CategoriesAdapter(
            requireActivity(),
            this::openArticlesByCategory
        )
        val dividerItemDecoration = SpaceItemDecoration(32)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_search)?.run {
            expandActionView()
            actionView.clearFocus()
            searchView = (actionView as SearchView).apply {
                queryHint = getString(queryHintResourceId())
                onActionViewExpanded()
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.setSearchQuery(it) }
                        return true
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setABTitle(getString(R.string.text_categories))
    }

    @StringRes
    private fun queryHintResourceId() = R.string.text_search_tegories

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect(::handleUiState)
        }
    }

    private fun handleUiState(categories: List<CategoryEntry>) = adapter?.setCategories(categories)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word =
                    CategoryEntry(it.getStringExtra(NewWordActivity.EXTRA_REPLY) ?: "", "human")
                viewModel.insert(word)
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
        parentFragmentManager.addFragmentToStack(ArticlesByCategoryFragment.newInstance(categoryId))
    }


    companion object {
        const val newWordActivityRequestCode = 1
        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

}