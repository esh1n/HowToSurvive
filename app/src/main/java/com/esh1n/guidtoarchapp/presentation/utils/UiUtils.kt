package com.esh1n.guidtoarchapp.presentation.utils

import android.content.Context
import android.view.View

object UiUtils {
    fun getCategoryImage(context: Context, iconId: String, prefix: String = "ic_"): Int {
        val res = "${prefix}category_$iconId"
        return context.resources.getIdentifier(res, "drawable", context.packageName)
    }

    fun getImageByPath(context: Context, iconId: String): Int {
        return context.resources.getIdentifier(iconId, "drawable", context.packageName)
    }

    fun View.click(action: (() -> Unit)?) {
        setOnClickListener { action?.invoke() }
    }
}
