package com.aaglobal.jnc_playground.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import com.esh1n.guidtoarchapp.presentation.utils.livedata.LiveDataFactory
import kotlinx.coroutines.launch


class SplashViewModel : ViewModel() {

    companion object {
        private const val SPLASH_DELAY_IN_MS = 500L
    }


    private val authRepository = GlobalDI.getAuthRepository()

    private val _splashNavCommand = LiveDataFactory.mutableEvent<SplashNavCommand>()

    val splashNavCommand: LiveData<SplashNavCommand?> = _splashNavCommand

    fun onSplashShown() {
        viewModelScope.launch {

            val navCommand = if (authRepository.hasAuthData()) {
                SplashNavCommand.NAVIGATE_TO_MAIN
            } else {
                SplashNavCommand.NAVIGATE_TO_ONBOARDING
            }
            _splashNavCommand.postValue(navCommand)
        }
    }

}