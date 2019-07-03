package com.ssgsag.ui.main.myPage

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssgsag.R

@BindingAdapter("profileImg")
fun setUserInfoImg(view: ImageView, userProfileUrl: String?) {
    Glide.with(view.context)
        .load(userProfileUrl)
        .error(R.drawable.img_default_profile)
        .apply(RequestOptions().circleCrop())
        .into(view)
}