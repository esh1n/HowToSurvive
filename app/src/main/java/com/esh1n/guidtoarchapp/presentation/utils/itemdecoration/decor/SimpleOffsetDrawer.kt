package com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.Decorator

class SimpleOffsetDrawer(
    private val left: Int = 0,
    private val top: Int = 0,
    private val right: Int = 0,
    private val bottom: Int = 0
) : Decorator.OffsetDecor {

    constructor(offset: Int) : this(offset, offset, offset, offset)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(left, top, right, bottom)
    }
}