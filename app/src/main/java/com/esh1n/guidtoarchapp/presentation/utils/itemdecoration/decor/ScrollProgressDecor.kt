package com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor

import android.graphics.Canvas
import android.util.Log
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.Decorator

abstract class ScrollProgressDecor : Decorator.RecyclerViewDecor {

    abstract fun draw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        @FloatRange(from = .0, to = 1.0) progress: Float
    )

    override fun draw(canvas: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {

        val currentOffset = recyclerView.computeVerticalScrollOffset()
        val maxOffset =
            recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent()
        val scrollProgress: Float = when (maxOffset) {
            0 -> 0.0F
            else -> currentOffset.toFloat() / maxOffset
        }
        Log.d(
            "CircleCI",
            "currentOffset ${currentOffset} maxOffset ${maxOffset} scrollProgress ${scrollProgress}"
        )
        draw(canvas, recyclerView, state, scrollProgress)
    }
}