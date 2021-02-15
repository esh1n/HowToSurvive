package com.esh1n.guidtoarchapp.presentation.categories

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.CategoryEntry
import com.esh1n.guidtoarchapp.presentation.utils.SpaceItemDecoration
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.flow.collect

class SearchCategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val viewModel: CategoryViewModel by viewModels()

    private var searchView: SearchView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val dividerItemDecoration =
            SpaceItemDecoration(32)
        with(recyclerview) {
            this.adapter = CategoriesAdapter(
                requireActivity(),
                ::openArticlesByCategory
            )
            addItemDecoration(dividerItemDecoration)
            layoutManager = LinearLayoutManager(requireActivity())
        }

        viewLifecycleOwner.lifecycle.addObserver(object :
            LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                getParentToolbar()?.run {
                    inflateMenu(R.menu.menu_main)
                    menu?.let(::setupSearchView)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                getParentToolbar()?.menu?.clear()
            }
        })
        observeData()
    }

    private fun getParentToolbar(): Toolbar? = parentFragment?.parentFragment?.view?.toolbar

    private fun setupSearchView(menu: Menu) {
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


    @StringRes
    private fun queryHintResourceId() = R.string.text_search_tegories

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect(::handleUiState)
        }
    }

    private fun handleUiState(categories: List<CategoryEntry>) {
        recyclerview.adapter<CategoriesAdapter>().setCategories(categories)
    }


    private fun openArticlesByCategory(category: CategoryEntry) {
        val categoryId = category.name
        findNavController().navigate(
            SearchCategoriesFragmentDirections.actionSearchContainerFragmentToArticlesByCategoryFragment(
                categoryId
            )
        )
    }
}