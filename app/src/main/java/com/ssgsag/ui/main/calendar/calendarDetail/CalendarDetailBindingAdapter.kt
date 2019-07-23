package com.ssgsag.ui.main.calendar.calendarDetail

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssgsag.R
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.util.extensionFunction.getDateInfo
import com.ssgsag.util.extensionFunction.getItemBase

//View BindingAdapter
@Suppress("UNCHECKED_CAST")
@BindingAdapter("calendarDetailReplaceAll")
fun RecyclerView.replaceAll(poster: PosterDetail?) {
    (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.run {
        replaceAll(getItemBase(poster))
        notifyDataSetChanged()
    }
}

@BindingAdapter("commentUserImg")
fun setCommentUserImg(view: ImageView, imgUrl: String?) {
    Glide.with(view.context)
        .load(imgUrl)
        .error(R.drawable.user_anonymous)
        .apply(RequestOptions().circleCrop())
        .into(view)
}

@BindingAdapter("likeBtnSrc")
fun setLikeBtnSrc(view: ImageView, isLike: Int?) {
    isLike?.run {
        if (isLike == 1)
            view.setImageResource(R.drawable.like)
        else
            view.setImageResource(R.drawable.like_passive)
    }
}

@BindingAdapter("likeCntText")
fun setLikeCntText(view: TextView, likeNum: Int?) {
    view.text = "좋아요 ${likeNum}개"
}