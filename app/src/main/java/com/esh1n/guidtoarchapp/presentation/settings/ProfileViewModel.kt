package com.esh1n.guidtoarchapp.presentation.settings

import androidx.lifecycle.ViewModel
import com.esh1n.guidtoarchapp.presentation.mvibase.UiState
import com.esh1n.guidtoarchapp.presentation.mvibase.UiWish

class ProfileViewModel : ViewModel() {
}

sealed class Wish : UiWish {
    data class OnEmailEntering(val email: String) : Wish()
    data class OnPhoneEntering(val phone: Boolean) : Wish()
    object OnSaveChanges : Wish()
    object OnLoadProfile : Wish()
}

data class State(
    val email: String,
    val phone: String,
    val isSaveChangesButtonEnabled: Boolean
) : UiState