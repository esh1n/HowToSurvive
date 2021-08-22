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
import com.esh1n.guidtoarchapp.presentation.utils.UiUtils.adapter
import com.esh1n.guidtoarchapp.presentation.utils.getColorFromAttribute
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.Decorator
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor.*
import com.esh1n.guidtoarchapp.presentation.utils.toPixels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchCategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val viewModel: CategoryViewModel by viewModels()

    private var searchView: SearchView? = null

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerview) {
            this.adapter = CategoriesAdapter(
                requireActivity(),
                ::openArticlesByCategory
            )
            addItemDecoration(decorator)
            layoutManager = LinearLayoutManager(requireActivity())
        }

        viewLifecycleOwner.lifecycle.addObserver(object :
            LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                getParentToolbar()?.run {
                    menu?.clear()
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
            //expandActionView()
            actionView.clearFocus()
            searchView = (actionView as SearchView).apply {
                queryHint = getString(queryHintResourceId())
                //onActionViewExpanded()
                //onActionViewCollapsed()
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

    @ExperimentalCoroutinesApi
    @kotlinx.coroutines.FlowPreview
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
        //  val emailCardDetailTransitionName = getString(R.string.category_card_detail_transition_name)
        //  val extras = FragmentNavigatorExtras(view to emailCardDetailTransitionName)
        findNavController().navigate(
            SearchCategoriesFragmentDirections.actionSearchContainerFragmentToArticlesByCategoryFragment(
                categoryId
            )
        )
    }

    private val horizontalOffsetDecor by lazy {

        SimpleOffsetDrawer(
            left = requireContext().toPixels(16),
            right = requireContext().toPixels(16)
        )
    }

    private val horizontalAndVerticalOffsetDecor by lazy {

        SimpleOffsetDrawer(
            left = requireContext().toPixels(16),
            top = requireContext().toPixels(8),
            right = requireContext().toPixels(16),
            bottom = requireContext().toPixels(8)
        )
    }

    private val dividerDrawer2Dp by lazy {
        LinearDividerDrawer(
            Gap(
                requireContext().getColorFromAttribute(R.attr.colorOnSurface),
                requireContext().toPixels(1),
                paddingStart = requireContext().toPixels(16),
                paddingEnd = requireContext().toPixels(16),
                rule = Rules.MIDDLE
            )
        )
    }

    private val roundDecor by lazy {
        RoundDecor(
            requireContext().toPixels(12).toFloat(),
            roundPolitic = RoundPolitic.SingleGroup()
        )
    }
    private val paralaxDecor by lazy {
        ParallaxDecor(requireContext(), R.drawable.ic_save_masks)
    }

    private val decorator by lazy {
        Decorator.Builder()
            .underlay(CategoriesAdapter.BANNER_ITEM_TYPE to paralaxDecor)
            .overlay(CategoriesAdapter.CATEGORY_ITEM_TYPE to roundDecor)
            .offset(CategoriesAdapter.CATEGORY_ITEM_TYPE to horizontalAndVerticalOffsetDecor)
            .offset(CategoriesAdapter.BANNER_ITEM_TYPE to horizontalOffsetDecor)
            //.overlay(CategoriesAdapter.CATEGORY_ITEM_TYPE to dividerDrawer2Dp)
            .build()
    }
}