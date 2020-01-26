package com.esh1n.guidtoarchapp.presentation.utils

import android.content.Context

object UiUtils {
    fun getCategoryImage(context: Context,iconId: String, prefix: String = "ic_"): Int {
        val res = "${prefix}category_$iconId"
        return context.resources.getIdentifier(res, "drawable", context.packageName)
    }

    fun getImageByPath(context: Context, iconId: String): Int {
        return context.resources.getIdentifier(iconId, "drawable", context.packageName)
    }
}
