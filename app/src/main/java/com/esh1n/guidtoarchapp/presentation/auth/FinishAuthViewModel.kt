package com.esh1n.guidtoarchapp.presentation.auth

import androidx.lifecycle.ViewModel
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI


class FinishAuthViewModel : ViewModel() {

    private val authRepository = GlobalDI.getAuthRepository()


    fun setFinishAuthFlag() {
        authRepository.putHasAuthData(true)
    }

}