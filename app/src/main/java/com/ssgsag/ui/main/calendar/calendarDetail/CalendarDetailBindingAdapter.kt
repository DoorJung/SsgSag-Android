package com.ssgsag.ui.main.calendar.calendarDetail

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.posterDetail.PosterDetail
import com.ssgsag.util.extension.getItemBase

//View BindingAdapter
@Suppress("UNCHECKED_CAST")
@BindingAdapter("calendarDetailReplaceAll")
fun RecyclerView.replaceAll(poster: PosterDetail?) {
    (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.run {
        replaceAll(getItemBase(poster))
        notifyDataSetChanged()
    }
}