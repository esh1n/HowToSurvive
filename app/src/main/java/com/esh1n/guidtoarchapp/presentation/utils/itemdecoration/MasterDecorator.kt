package com.esh1n.guidtoarchapp.presentation.utils.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Main item decorator for drawing all decors of RecyclerView and ViewHolders decors
 */
class MasterDecorator(private val decorsBridge: DecorsBridge) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        decorsBridge.onDrawOverlay(canvas, parent, state)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        decorsBridge.onDrawUnderlay(canvas, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        decorsBridge.getItemOffsets(outRect, view, parent, state)
    }
}