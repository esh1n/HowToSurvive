package com.esh1n.guidtoarchapp.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <Value> LiveData<Value>.observeNonNull(owner: LifecycleOwner, consumer: (Value) -> Unit) =
    observe(owner, { it?.let(consumer) })

fun <Value> LiveData<Value>.observeNullable(owner: LifecycleOwner, consumer: (Value?) -> Unit) =
    observe(owner, consumer)