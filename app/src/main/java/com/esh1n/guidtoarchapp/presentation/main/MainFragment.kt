package com.esh1n.guidtoarchapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.utils.setupWithNavController
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Main fragment -- container for bottom navigation
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private var currentNavController: LiveData<NavController>? = null


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.search_nav_graph,
            R.navigation.favorites_nav_graph,
            R.navigation.phones_nav_graph,
            R.navigation.profile_nav_graph
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = fragment_main__bottom_navigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.fragment_main__nav_host_container,
            intent = requireActivity().intent
        )
        // Whenever the selected controller changes, setup the action bar.
        controller.observe(viewLifecycleOwner, { navController ->
            //val appBarConfiguration = AppBarConfiguration(setOf(R.id.menu_search,R.id.menu_favorites,R.id.menu_phones,R.id.menu_profile))
            //toolbar.setupWithNavController(navController, appBarConfiguration)
            toolbar.setupWithNavController(navController)
        })
        currentNavController = controller
    }

}