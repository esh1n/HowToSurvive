package com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.Decorator

class ParallaxDecor(
    context: Context,
    @DrawableRes resId: Int
) : Decorator.ViewHolderDecor {

    private val image: Bitmap? = AppCompatResources.getDrawable(context, resId)?.toBitmap()

    override fun draw(
        canvas: Canvas,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {

        val offset = view.top / 2
        image?.let { btm ->
            canvas.drawBitmap(
                btm,
                Rect(0, offset, view.width, view.height + offset),
                Rect(view.left, view.top, view.right, view.bottom),
                null
            )
        }
    }
}
