package com.ssgsag.util.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager


class NonScrollGridLayoutManagerManager(context: Context, spanCount: Int) :
    GridLayoutManager(context, spanCount) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}