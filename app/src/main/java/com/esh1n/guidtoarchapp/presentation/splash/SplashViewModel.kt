package com.aaglobal.jnc_playground.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esh1n.guidtoarchapp.domain.IAuthRepo
import com.esh1n.guidtoarchapp.domain.IPrefsRepo
import com.esh1n.guidtoarchapp.domain.PrepopulateDBUseCase
import com.esh1n.guidtoarchapp.presentation.utils.livedata.LiveDataFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: IAuthRepo,
    private val prefsRepo: IPrefsRepo,
    private val prepopulateDBUseCase: PrepopulateDBUseCase
) : ViewModel() {

    companion object {
        private const val SPLASH_DELAY_IN_MS = 50L
    }

    private val _splashNavCommand = LiveDataFactory.mutableEvent<SplashNavCommand>()

    val splashNavCommand: LiveData<SplashNavCommand?> = _splashNavCommand

    fun onSplashShown() {
        viewModelScope.launch {
            delay(SPLASH_DELAY_IN_MS)
            if (prefsRepo.firstLaunch) {
                prepopulateDBUseCase.fillDB()
            }
            val navCommand = if (authRepository.hasAuth) {
                SplashNavCommand.NAVIGATE_TO_MAIN
            } else {
                SplashNavCommand.NAVIGATE_TO_ONBOARDING
            }
            _splashNavCommand.postValue(navCommand)
        }
    }

}