package com.ssgsag.ui.main.myPage.subscribe

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ssgsag.R
import com.ssgsag.data.model.subscribe.Subscribe
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor

@BindingAdapter("subscribeOval")
fun setSubscribeOval(view: View, subscribe: Subscribe) {
    if(subscribe.userIdx != 0) {
        val oval = ShapeDrawable(OvalShape())
        oval.setColorFilter(Color.parseColor(subscribe.interestUrl), PorterDuff.Mode.SRC_OVER)
        view.background = oval
    } else {
        val oval = ShapeDrawable(OvalShape())
        oval.setColorFilter(view.context.getColor(R.color.subscribe_inactive), PorterDuff.Mode.SRC_OVER)
        view.background = oval
    }
}

@BindingAdapter("subscribeNameColor")
fun setSubscribeNameColor(view: TextView, userIdx: Int) {
    if(userIdx != 0) {
        view.textColor = view.context.getColor(R.color.subscribe_active_name)
    } else {
        view.textColor = view.context.getColor(R.color.subscribe_inactive)
    }
}

@BindingAdapter("subscribeTag")
fun setSubscribeTagColor(view: TextView, userIdx: Int) {
    if(userIdx != 0) {
        view.textColor = view.context.getColor(R.color.subscribe_active_tag)
    } else {
        view.textColor = view.context.getColor(R.color.subscribe_inactive)
    }
}

@BindingAdapter("subscribeBtn")
fun setSubscribeBtn(view: ImageView, userIdx: Int) {
    if(userIdx != 0) {
        view.imageResource = R.drawable.bt_follow
    } else {
        view.imageResource = R.drawable.bt_unfollow
    }
}