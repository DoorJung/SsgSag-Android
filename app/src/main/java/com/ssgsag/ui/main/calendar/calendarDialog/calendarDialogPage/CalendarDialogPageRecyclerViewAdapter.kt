package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.content.Context
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssgsag.R
import com.ssgsag.data.model.poster.posterDetail.comment.Comment
import com.ssgsag.data.model.schedule.Schedule
import com.ssgsag.databinding.ItemCalDetailCommentBinding
import com.ssgsag.databinding.ItemCalendarScheduleBinding

class CalendarDialogPageRecyclerViewAdapter(
    val items: ArrayList<Schedule>
) : RecyclerView.Adapter<CalendarDialogPageRecyclerViewAdapter.ViewHolder>() {

    private var listener: OnScheduleItemClickListener? = null

    fun setOnScheduleItemClickListener(listener: OnScheduleItemClickListener) {
        this.listener = listener
    }

    fun replaceAll(items: ArrayList<Schedule>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemCalendarScheduleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_calendar_schedule, parent, false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.schedule = items[position]
        holder.dataBinding.root.setOnClickListener {
            listener?.onItemClicked(items[position].posterIdx)
        }
        holder.dataBinding.itemCalendarScheduleIvLike.setOnClickListener {
            listener?.onBookmarkClicked(items[position].posterIdx, items[position].isFavorite)
        }

    }

    inner class ViewHolder(val dataBinding: ItemCalendarScheduleBinding) : RecyclerView.ViewHolder(dataBinding.root)

    interface OnScheduleItemClickListener {
        fun onItemClicked(posterIdx: Int)
        fun onBookmarkClicked(posterIdx: Int, isFavorite: Int)
    }

}