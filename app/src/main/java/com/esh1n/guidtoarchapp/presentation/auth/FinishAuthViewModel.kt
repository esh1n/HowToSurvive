package com.esh1n.guidtoarchapp.presentation.auth

import androidx.lifecycle.ViewModel
import com.esh1n.guidtoarchapp.domain.IAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FinishAuthViewModel @Inject constructor(private val iAuthRepo: IAuthRepo) : ViewModel() {

    fun setFinishAuthFlag() {
        iAuthRepo.updateAuth(true)
    }
}