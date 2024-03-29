package com.ssgsag.ui.main.ssgSag

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.item.ItemBase
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.databinding.ItemSsgsagDetailBinding
import com.ssgsag.util.extensionFunction.getItemBase
import org.jetbrains.anko.imageResource

@Suppress("UNCHECKED_CAST")
@BindingAdapter("poster_detail")
fun setPosterDetail(view: RecyclerView, poster: PosterDetail?) {
    view.run {
        adapter =
            object : BaseRecyclerViewAdapter<ItemBase, ItemSsgsagDetailBinding>() {
                override val layoutResID: Int
                    get() = R.layout.item_ssgsag_detail
                override val bindingVariableId: Int
                    get() = BR.ssgSagDetail
                override val listener: OnItemClickListener?
                    get() = null
            }
        (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.run {
            replaceAll(getItemBase(poster))
            notifyDataSetChanged()
        }
    }
}

@BindingAdapter("ssgSagHeaderBg")
fun setSsgSagHeaderBg(view: CardView, categoryIdx: Int?) {
    when (categoryIdx) {
        0 -> view.setCardBackgroundColor(view.context.getColor(R.color.contest))
        1 -> view.setCardBackgroundColor(view.context.getColor(R.color.act))
        2 -> view.setCardBackgroundColor(view.context.getColor(R.color.club))
        3 -> view.setCardBackgroundColor(view.context.getColor(R.color.notice))
        4 -> view.setCardBackgroundColor(view.context.getColor(R.color.recruit))
        5 -> view.setCardBackgroundColor(view.context.getColor(R.color.etc))
        6 -> view.setCardBackgroundColor(view.context.getColor(R.color.club))
        7 -> view.setCardBackgroundColor(view.context.getColor(R.color.club))
    }
}

@BindingAdapter("posterDday")
fun setPosterDday(view: TextView, dday: String?) {
    dday?.let {
        if (it.toInt() != 0)
            view.text = "D - ${dday}"
        else
            view.text = "D - Day"
    }
}

//@BindingAdapter("poster_cate_img")
//fun setPosterCateImg(view: ImageView, poster: PosterDetail?) {
//    poster?.let {
//        when (it.categoryIdx) {
//            0 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_contest
//                else
//                    view.imageResource = R.drawable.ic_circle_today_contest
//            }
//            1 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_act
//                else
//                    view.imageResource = R.drawable.ic_circle_today_act
//            }
//            2 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_club
//                else
//                    view.imageResource = R.drawable.ic_circle_today_club
//            }
//            3 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_notice
//                else
//                    view.imageResource = R.drawable.ic_circle_today_notice
//            }
//            4 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_recruit
//                else
//                    view.imageResource = R.drawable.ic_circle_today_recruit
//            }
//            5 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_etc
//                else
//                    view.imageResource = R.drawable.ic_circle_today_etc
//            }
//            6 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_club
//                else
//                    view.imageResource = R.drawable.ic_circle_today_club
//            }
//            7 -> {
//                if (it.dday.toInt() != 0)
//                    view.imageResource = R.drawable.ic_circle_deadline_club
//                else
//                    view.imageResource = R.drawable.ic_circle_today_club
//            }
//        }
//    }
//}