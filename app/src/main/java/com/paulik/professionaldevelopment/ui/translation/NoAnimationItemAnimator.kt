package com.paulik.professionaldevelopment.ui.translation

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class NoAnimationItemAnimator : DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        dispatchAnimationFinished(oldHolder)
        dispatchAnimationFinished(newHolder)
        return false
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        dispatchAnimationFinished(holder)
        return false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        dispatchAnimationFinished(holder)
        return false
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        targetX: Int,
        targetY: Int,
        oldX: Int,
        oldY: Int
    ): Boolean {
        dispatchAnimationFinished(holder)
        return false
    }
}