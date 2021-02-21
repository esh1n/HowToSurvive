package com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.Decorator
import com.esh1n.guidtoarchapp.presentation.utils.itemdecoration.decor.round.RoundOutlineProvider
import ru.ozh.recycler.decorator.list.decor.round.RoundMode

class RoundDecor(
    private val cornerRadius: Float,
    private val roundPolitic: RoundPolitic = RoundPolitic.Every(RoundMode.ALL)
) : Decorator.ViewHolderDecor {

    override fun draw(
        canvas: Canvas,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {

        val viewHolder = recyclerView.getChildViewHolder(view)
        val nextViewHolder =
            recyclerView.findViewHolderForAdapterPosition(viewHolder.adapterPosition + 1)
        val previousChildViewHolder =
            recyclerView.findViewHolderForAdapterPosition(viewHolder.adapterPosition - 1)

        if (cornerRadius.compareTo(0f) != 0) {
            val roundMode = getRoundMode(previousChildViewHolder, viewHolder, nextViewHolder)
            val outlineProvider = view.outlineProvider
            if (outlineProvider is RoundOutlineProvider) {
                outlineProvider.roundMode = roundMode
                view.invalidateOutline()
            } else {
                view.outlineProvider = RoundOutlineProvider(cornerRadius, roundMode)
                view.clipToOutline = true
            }
        }
    }

    private fun getRoundMode(
        previousChildViewHolder: RecyclerView.ViewHolder?,
        currentViewHolder: RecyclerView.ViewHolder?,
        nextChildViewHolder: RecyclerView.ViewHolder?
    ): RoundMode {

        val previousHolderItemType = previousChildViewHolder?.itemViewType ?: UNDEFINE_VIEW_HOLDER
        val currentHolderItemType = currentViewHolder?.itemViewType ?: UNDEFINE_VIEW_HOLDER
        val nextHolderItemType = nextChildViewHolder?.itemViewType ?: UNDEFINE_VIEW_HOLDER

        if (roundPolitic is RoundPolitic.Every) return roundPolitic.roundMode

        if (roundPolitic is RoundPolitic.SingleGroup) {
            return when {
                previousHolderItemType == UNDEFINE_VIEW_HOLDER && currentHolderItemType == nextHolderItemType -> RoundMode.TOP
                nextHolderItemType == UNDEFINE_VIEW_HOLDER && currentHolderItemType == previousHolderItemType -> RoundMode.BOTTOM
                else -> RoundMode.NONE
            }
        }

        return when {
            previousHolderItemType != currentHolderItemType && currentHolderItemType != nextHolderItemType -> RoundMode.ALL
            previousHolderItemType != currentHolderItemType && currentHolderItemType == nextHolderItemType -> RoundMode.TOP
            previousHolderItemType == currentHolderItemType && currentHolderItemType != nextHolderItemType -> RoundMode.BOTTOM
            previousHolderItemType == currentHolderItemType && currentHolderItemType == nextHolderItemType -> RoundMode.NONE
            else -> RoundMode.NONE
        }
    }
}

sealed class RoundPolitic {
    class Every(val roundMode: RoundMode) : RoundPolitic()
    class Group() : RoundPolitic()
    class SingleGroup() : RoundPolitic()
}
