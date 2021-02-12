package com.esh1n.guidtoarchapp.presentation

import androidx.lifecycle.ViewModel
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI

class ThemeSwitcherViewModel : ViewModel() {

    private val repo = GlobalDI.getPreferenceRepository()

    val nighModeLiveData = repo.nightModeLive

    val isDarkThemeLive = repo.isDarkThemeLive

    fun setIsDarkTheme(isDark: Boolean) {
        repo.isDarkTheme = isDark
    }

}