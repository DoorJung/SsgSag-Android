package com.ssgsag.util.dataBinging

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssgsag.R
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.util.extensionFunction.dayOfWeekExtension
import com.ssgsag.util.extensionFunction.getDateInfo
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

//View BindingAdapter
@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.run {
        replaceAll(list)
        notifyDataSetChanged()
    }
}

@BindingAdapter("noDataDirective")
fun setNoDataDirective(view: TextView, data: List<Any>?) {
    data?.run {
        if (size == 0) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}

@BindingAdapter("glideImg")
fun setGlideImg(view: ImageView, imgUrl: String?) {
    Glide.with(view.context)
        .load(imgUrl)
        //.error(R.drawable.img_poster) //에러시 나올 이미지 적용
        .into(view)
}

//Category BindingAdapter
@BindingAdapter("textColorByCate")
fun setTextColorByCate(view: TextView, categoryIdx: Int?) {
    when (categoryIdx) {
        0 -> view.textColor = view.context.getColor(R.color.contest)
        1 -> view.textColor = view.context.getColor(R.color.act)
        2 -> view.textColor = view.context.getColor(R.color.club)
        3 -> view.textColor = view.context.getColor(R.color.notice)
        4 -> view.textColor = view.context.getColor(R.color.recruit)
        5 -> view.textColor = view.context.getColor(R.color.etcText)
        6 -> view.textColor = view.context.getColor(R.color.club)
        7 -> view.textColor = view.context.getColor(R.color.club)
    }
}

@BindingAdapter("textByCate")
fun setTextByCate(view: TextView, categoryIdx: Int?) {
    when (categoryIdx) {
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

@BindingAdapter("cateCardBg")
fun setCateBg(view: CardView, categoryIdx: Int?) {
    when (categoryIdx) {
        0 -> view.setCardBackgroundColor(view.context.getColor(R.color.contestBg))
        1 -> view.setCardBackgroundColor(view.context.getColor(R.color.actBg))
        2 -> view.setCardBackgroundColor(view.context.getColor(R.color.clubBg))
        3 -> view.setCardBackgroundColor(view.context.getColor(R.color.noticeBg))
        4 -> view.setCardBackgroundColor(view.context.getColor(R.color.recruitBg))
        5 -> view.setCardBackgroundColor(view.context.getColor(R.color.etcBg))
        6 -> view.setCardBackgroundColor(view.context.getColor(R.color.clubBg))
        7 -> view.setCardBackgroundColor(view.context.getColor(R.color.clubBg))
    }
}

@BindingAdapter("cateBg")
fun setCateBg(view: View, categoryIdx: Int?) {
    when (categoryIdx) {
        0 -> view.backgroundColor = view.context.getColor(R.color.contest)
        1 -> view.backgroundColor = view.context.getColor(R.color.act)
        2 -> view.backgroundColor = view.context.getColor(R.color.club)
        3 -> view.backgroundColor = view.context.getColor(R.color.notice)
        4 -> view.backgroundColor = view.context.getColor(R.color.recruit)
        5 -> view.backgroundColor = view.context.getColor(R.color.etc)
        6 -> view.backgroundColor = view.context.getColor(R.color.club)
        7 -> view.backgroundColor = view.context.getColor(R.color.club)
    }
}

//date BindingAdapter
@BindingAdapter("startDateFormat1", "endDateFormat1")
fun setDateTextFormat1(view: TextView, posterStartDate: String?, posterEndDate: String?) {
    var startDate = ""
    var endDate = ""
    posterStartDate?.let {
        val startDateArr = arrayListOf(getDateInfo(it, 0), getDateInfo(it, 1), getDateInfo(it, 2))
        startDate = "${startDateArr[1]}.${startDateArr[2]}"
    }
    posterEndDate?.let {
        val endDateArr = arrayListOf(getDateInfo(it, 0), getDateInfo(it, 1), getDateInfo(it, 2))
        endDate = " ~ ${endDateArr[1]}.${endDateArr[2]}"
    }
    view.text = startDate + endDate
}

@BindingAdapter("startDateFormat2", "endDateFormat2")
fun setDateTextFormat2(view: TextView, posterStartDate: String?, posterEndDate: String?) {
    var startDate = ""
    var endDate = ""
    posterStartDate?.let {
        val startDateArr = arrayListOf(getDateInfo(it, 0), getDateInfo(it, 1), getDateInfo(it, 2))
        startDate = "${startDateArr[1]}.${startDateArr[2]}(${dayOfWeekExtension(startDateArr)})"
    }
    posterEndDate?.let {
        val endDateArr = arrayListOf(getDateInfo(it, 0), getDateInfo(it, 1), getDateInfo(it, 2))
        endDate = " ~ ${endDateArr[1]}.${endDateArr[2]}(${dayOfWeekExtension(endDateArr)})"
    }
    view.text = startDate + endDate
}