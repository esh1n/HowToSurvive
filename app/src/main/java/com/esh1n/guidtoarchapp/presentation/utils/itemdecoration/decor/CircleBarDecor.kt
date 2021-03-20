package com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.recyclerview.widget.RecyclerView

class CircleBarDecor(private val circleDiameter: Int, private val color: Int) :
    ScrollProgressDecor() {

    private val paint = Paint()
        .apply {
            isAntiAlias = true
            color = this@CircleBarDecor.color
        }


    private val progressBarRect = RectF()
    private var scrollRange = 0

    override fun draw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        progress: Float
    ) {

        val (cX, cY) = recyclerView.measuredWidth / 2 to recyclerView.measuredHeight / 2

        with(progressBarRect) {
            this.top = (cY - (circleDiameter / 2)).toFloat()
            this.right = (cX + (circleDiameter / 2)).toFloat()
            this.bottom = (cY + (circleDiameter / 2)).toFloat()
            this.left = (cX - (circleDiameter / 2)).toFloat()
        }

        canvas.drawArc(progressBarRect, 270f, progress * 360, true, paint)

    }
}