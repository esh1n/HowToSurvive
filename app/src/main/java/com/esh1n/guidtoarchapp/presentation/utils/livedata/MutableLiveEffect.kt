package com.esh1n.guidtoarchapp.presentation.utils.livedata

import androidx.lifecycle.LifecycleOwner
import com.esh1n.guidtoarchapp.presentation.utils.observeNonNull

class MutableLiveEffect {
    private val liveEvent = MutableLiveEvent<Any>()

    fun happen() {
        liveEvent.value = Any()
    }

    fun postHappen() {
        liveEvent.postValue(Any())
    }

    fun observe(owner: LifecycleOwner, action: () -> Unit) {
        liveEvent.observeNonNull(owner) { action() }
    }
}