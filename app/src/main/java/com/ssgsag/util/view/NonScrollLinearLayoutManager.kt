package com.ssgsag.util.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class NonScrollLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}