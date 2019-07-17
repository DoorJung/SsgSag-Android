package com.ssgsag.ui.main.calendar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.ssgsag.R
import org.jetbrains.anko.textColor

//Category BindingAdapter
@BindingAdapter("checkedSortTextColor", "sortIsChecked")
fun setCheckedSortTextColor(view: TextView, categoryIdx: Int?, sortIsChecked: Boolean) {
    if (sortIsChecked) {
        when (categoryIdx) {
            -2 -> view.textColor = Color.parseColor("#626aff")
            -1 -> view.textColor = Color.parseColor("#626aff")
            0 -> view.textColor = view.context.getColor(R.color.contest)
            1 -> view.textColor = view.context.getColor(R.color.act)
            2 -> view.textColor = view.context.getColor(R.color.club)
            3 -> view.textColor = view.context.getColor(R.color.notice)
            4 -> view.textColor = view.context.getColor(R.color.recruit)
            5 -> view.textColor = view.context.getColor(R.color.etcText)
            6 -> view.textColor = view.context.getColor(R.color.club)
            7 -> view.textColor = view.context.getColor(R.color.club)
        }
    } else {
        view.textColor = Color.parseColor("#d7d8d8")
    }
}

@BindingAdapter("checkedSortText")
fun setCheckedSortText(view: TextView, categoryIdx: Int?) {
    when (categoryIdx) {
        -2 -> view.text = "전체"
        -1 -> view.text = "즐겨찾기"
        0 -> view.text = "공모전"
        1 -> view.text = "대외활동"
        2 -> view.text = "동아리"
        3 -> view.text = "교내공지"
        4 -> view.text = "채용"
        5 -> view.text = "기타"
        6 -> view.text = "동아리"
        7 -> view.text = "슥삭"
    }
}

@BindingAdapter("checkedSortCardBg", "sortIsChecked")
fun setCheckedSortCardBg(view: CardView, categoryIdx: Int?, sortIsChecked: Boolean) {
    if (sortIsChecked) {
        when (categoryIdx) {
            0 -> view.setCardBackgroundColor(view.context.getColor(R.color.contestBg))
            1 -> view.setCardBackgroundColor(view.context.getColor(R.color.actBg))
            2 -> view.setCardBackgroundColor(view.context.getColor(R.color.clubBg))
            3 -> view.setCardBackgroundColor(view.context.getColor(R.color.noticeBg))
            4 -> view.setCardBackgroundColor(view.context.getColor(R.color.recruitBg))
            5 -> view.setCardBackgroundColor(view.context.getColor(R.color.etcBg))
            6 -> view.setCardBackgroundColor(view.context.getColor(R.color.clubBg))
            7 -> view.setCardBackgroundColor(view.context.getColor(R.color.clubBg))
            else -> view.setCardBackgroundColor(null)
        }
    } else
        view.setCardBackgroundColor(null)
}

@BindingAdapter("sortIndicator", "sortIsChecked")
fun setSortIndicator(view: View, categoryIdx: Int?, sortIsChecked: Boolean) {
    when (categoryIdx) {
        -2, -1 -> if (sortIsChecked) view.visibility = View.VISIBLE else view.visibility = View.INVISIBLE
        else -> view.visibility = View.INVISIBLE
    }

}