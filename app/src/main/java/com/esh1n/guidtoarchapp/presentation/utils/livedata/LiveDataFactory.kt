package com.esh1n.guidtoarchapp.presentation.utils.livedata

import androidx.lifecycle.MutableLiveData

object LiveDataFactory {

    fun <T> mutable(): MutableLiveData<T> = MutableLiveData()

    fun <T> mutable(defaultValue: T): MutableLiveData<T> =
        mutable<T>().apply { value = defaultValue }

    fun <T> singleObserverMutableEvent() = SingleObserverMutableLiveEvent<T>()

    fun <T> mutableEvent() = MutableLiveEvent<T>()

    fun <T> mutableEvent(defaultValue: T) = mutableEvent<T>().apply { value = defaultValue }

    fun mutableEffect() = MutableLiveEffect()
}