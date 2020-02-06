package com.esh1n.guidtoarchapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.fragment.MainFragmentTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*

class MainActivity : AppCompatActivity() {

    private var selectedItem: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupBottomNavigation()
        val restoredMenu = getSelectedFragmentMenuId(savedInstanceState)
        bottom_navigation.selectedItemId = restoredMenu.itemId
        initFragmentTransactionsListener()
    }

    private fun initFragmentTransactionsListener() {
        supportFragmentManager.addOnBackStackChangedListener { this.processFragmentsSwitching() }
    }

    private fun processFragmentsSwitching() {

        supportFragmentManager.let {
            val isInRootFragment = it.backStackEntryCount == 1
            //TODO move it to main activity
            //requireActivity().actionBar?.setDisplayHomeAsUpEnabled(!isInRootFragment)
            if (isInRootFragment) {
                updateTitleForRootFragment()
            }
        }

    }

    private fun updateTitleForRootFragment() {
        val titleForRootFragment = getTitleByTab(getTabByMenu(bottom_navigation.selectedItemId))
        setABTitle(titleForRootFragment)
    }

    public fun setABTitle(title: CharSequence) {
        supportActionBar?.title = title
    }

    private fun getSelectedFragmentMenuId(savedInstance: Bundle?): MenuItem {
        val isSavedDataExist = savedInstance != null
        return if (isSavedDataExist) {
            selectedItem = savedInstance?.getInt(SELECTED_ITEM, 0) ?: 0
            bottom_navigation.menu.findItem(selectedItem)
        } else {
            val checkedItem = bottom_navigation!!.selectedItemId
            bottom_navigation.menu.findItem(checkedItem)
        }
    }

    private fun setupBottomNavigation() {
        bottom_navigation.menu.clear() //clear old inflated items.
        bottom_navigation.inflateMenu(R.menu.bottom_nav_items)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            selectFragment(item)
            true
        }
    }

    private fun selectFragment(menuItem: MenuItem) {
        val menuId = menuItem.itemId
        selectedItem = menuId
        updateBottomMenuCheckedItem(menuId)

        with(getTabByMenu(menuId)) {
            replaceRootTabFragment(this)
            setABTitle(getTitleByTab(this))
        }
    }

    private fun getTitleByTab(tab: MainFragmentTab): CharSequence {

        val titleRes = when (tab) {
            MainFragmentTab.MAIN_SCREEN -> R.string.menu_main_screen
            MainFragmentTab.SAVED_ARTICLES -> R.string.menu_saved_articles
            MainFragmentTab.PHONES -> R.string.menu_saved_phones
        }
        return getString(titleRes)
    }

    private fun replaceRootTabFragment(tab: MainFragmentTab) {
        // Pop off everything up to and including the current tab
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.popBackStack(
            BACK_STACK_ROOT_TAG,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        // Add the new tab fragment
        // Add the new tab fragment
        fragmentManager.beginTransaction()
            .replace(R.id.container_fragment, tab.fragment, tab.name)
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .commit()
    }

    private fun getTabByMenu(menu: Int): MainFragmentTab {
        return when (menu) {
            R.id.menu_main_screen -> MainFragmentTab.MAIN_SCREEN
            R.id.menu_saved_articles -> MainFragmentTab.SAVED_ARTICLES
            R.id.menu_phones -> MainFragmentTab.PHONES
            else -> MainFragmentTab.PHONES
        }
    }

    private fun updateBottomMenuCheckedItem(checkedFragmentId: Int) {
        val menu = bottom_navigation.menu
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            menuItem.isChecked = menuItem.itemId == checkedFragmentId
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_ITEM, selectedItem)
        super.onSaveInstanceState(outState)
    }

    fun AppCompatActivity.setABTitle(title: CharSequence?) {
        if (!title.isNullOrBlank()) {
            supportActionBar?.title = title
        }
    }

    @Override
    override fun onBackPressed() {
        val fragments = supportFragmentManager
        //TODO разобраться val homeFrag = fragments.findFragmentByTag("0")
        if (fragments.backStackEntryCount > 1) { // We have fragments on the backstack that are poppable
            fragments.popBackStackImmediate()
        } else {
            supportFinishAfterTransition()
        }
    }

    companion object {
        private const val BACK_STACK_ROOT_TAG = "root_fragment"
        private const val SELECTED_ITEM = "arg_selected_item"
    }

}
