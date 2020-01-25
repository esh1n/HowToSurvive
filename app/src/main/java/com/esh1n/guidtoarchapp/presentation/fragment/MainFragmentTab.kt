package com.esh1n.guidtoarchapp.presentation.fragment


import androidx.fragment.app.Fragment
import com.esh1n.guidtoarchapp.presentation.CategoriesFragment

enum class MainFragmentTab(val value: Int) {
    MAIN_SCREEN(0), SAVED_ARTICLES(1), PHONES(2);

    val fragment: Fragment
        get() {
            return when (this) {
                MAIN_SCREEN -> CategoriesFragment.newInstance()
                SAVED_ARTICLES -> TabFragment.newInstance(this.value)
                PHONES -> TabFragment.newInstance(this.value)
            }
        }

    companion object {

        fun getByPosition(position: Int): MainFragmentTab {
            for (tab in values()) {
                if (tab.value == position) {
                    return tab
                }
            }
            throw IllegalArgumentException("No such tab")
        }
    }
}