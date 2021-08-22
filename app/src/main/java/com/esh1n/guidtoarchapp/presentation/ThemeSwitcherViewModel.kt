package com.esh1n.guidtoarchapp.presentation

import androidx.lifecycle.ViewModel
import com.esh1n.guidtoarchapp.domain.IPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeSwitcherViewModel @Inject constructor(private val prefsRepo: IPrefsRepo) : ViewModel() {

    val nighModeLiveData = prefsRepo.nightModeLive

    val isDarkThemeLive = prefsRepo.isDarkThemeLive

    fun setIsDarkTheme(isDark: Boolean) {
        prefsRepo.isDarkTheme = isDark
    }
}