package com.ssgsag.ui.main.calendar.calendarPage

import android.view.View
import androidx.databinding.BindingAdapter
import android.graphics.Point
import android.content.Context.WINDOW_SERVICE
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ssgsag.R
import com.ssgsag.data.model.date.Date
import org.jetbrains.anko.textColor


@BindingAdapter("calendar_height")
fun setHeight(view: View, lines: Int) {
    view.parent
    val layoutParams = view.layoutParams
    val wm = view.context.getSystemService(WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    val height = size.y// - 480

    layoutParams.height = ((height / lines) / 1.35).toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("calendar_date")
fun setDate(view: TextView, date: Date) {
    view.text = date.date
    if (date.isToDay) {
        view.textColor = view.context.getColor(R.color.white)
        val oval = ShapeDrawable(OvalShape())
        oval.setColorFilter(view.context.getColor(R.color.today), PorterDuff.Mode.SRC_OVER)
        view.background = oval
    } else {
        if (!date.isCurrentMonth)
            view.textColor = view.context.getColor(R.color.passiveMonth)
        else {
            if (date.dayOfWeek == 1)
                view.textColor = view.context.getColor(R.color.sunday)
            else if (date.dayOfWeek == 7)
                view.textColor = view.context.getColor(R.color.saturday)
            else
                view.textColor = view.context.getColor(R.color.activeMonth)
        }
    }
}

@BindingAdapter("date", "position")
fun setSchedule(view: CardView, date: Date, position: Int) {
    date.schedule?.let {
        if (it.size > position) {
            view.visibility = View.VISIBLE
            if (date.schedule!![position].isEnded == 1)
                view.setCardBackgroundColor(view.context.getColor(R.color.passive))
            else {
                when (date.schedule!![position].categoryIdx) {
                    0 -> view.setCardBackgroundColor(view.context.getColor(R.color.contest))
                    1 -> view.setCardBackgroundColor(view.context.getColor(R.color.act))
                    2, 6, 7 -> view.setCardBackgroundColor(view.context.getColor(R.color.club))
                    3 -> view.setCardBackgroundColor(view.context.getColor(R.color.notice))
                    4 -> view.setCardBackgroundColor(view.context.getColor(R.color.recruit))
                    5 -> view.setCardBackgroundColor(view.context.getColor(R.color.etcText))
                }
            }
        } else
            view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("date", "position")
fun setBookmarkBtnImgInCal(view: ImageView, date: Date, position: Int) {
    date.schedule?.let {
        if (it.size > position) {
            if (it[position].isFavorite == 1)
                view.visibility = View.VISIBLE
            else
                view.visibility = View.GONE
        } else
            view.visibility = View.GONE
    }
}

@BindingAdapter("date", "position")
fun setText(view: TextView, date: Date, position: Int) {

    date.schedule?.let {
        if (it.size > position) {
            view.text = it[position].posterName
        }
    }
}