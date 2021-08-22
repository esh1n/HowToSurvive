package com.esh1n.guidtoarchapp.domain

import androidx.lifecycle.LiveData

interface IPrefsRepo {
    val nightModeLive: LiveData<Int>
    var isDarkTheme: Boolean
    val isDarkThemeLive: LiveData<Boolean>
    val firstLaunch: Boolean
}