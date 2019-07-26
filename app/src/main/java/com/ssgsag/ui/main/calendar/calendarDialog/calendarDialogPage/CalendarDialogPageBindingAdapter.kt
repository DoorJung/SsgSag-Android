package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ssgsag.R

@BindingAdapter("bookmarkBtnImgInDialog")
fun setBookmarkBtnImgInDialog(view: ImageView, isFavorite: Int?) {
    isFavorite?.run {
        if (isFavorite == 1)
            view.setImageResource(R.drawable.favorite)
        else
            view.setImageResource(R.drawable.favorite_passive)
    }
}