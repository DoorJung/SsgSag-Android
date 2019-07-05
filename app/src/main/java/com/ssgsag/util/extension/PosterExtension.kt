package com.ssgsag.util.extension

import com.ssgsag.SsgSagApplication.Companion.ActTitle
import com.ssgsag.SsgSagApplication.Companion.ClubTitle
import com.ssgsag.SsgSagApplication.Companion.ContestTitle
import com.ssgsag.SsgSagApplication.Companion.RecruitTitle
import com.ssgsag.data.model.item.ItemBase
import com.ssgsag.data.model.posterDetail.PosterDetail
import org.json.JSONArray

fun getItemBase(poster: PosterDetail?): ArrayList<ItemBase> {
    val list: ArrayList<ItemBase> = ArrayList()
    when (poster?.categoryIdx) {
        0 -> dataInject(list, ContestTitle, poster)
        1 -> dataInject(list, ActTitle, poster)
        2, 6 -> dataInject(list, ClubTitle, poster)
        4 -> dataInject(list, RecruitTitle, poster)
        3, 5, 7 -> dataInject(list, null, poster)
    }
    return list
}


fun dataInject(titleContents: ArrayList<ItemBase>, titleByCate: ArrayList<String>?, poster: PosterDetail) {
    titleByCate?.let {
        titleContents.add(ItemBase(it[0], poster.outline))
        titleContents.add(ItemBase(it[1], poster.target))
        titleContents.add(ItemBase(it[2], poster.benefit))
    } ?: run {
        val ja = JSONArray(poster.outline)
        for (i in 0 until ja.length()) {
            val order = ja.getJSONObject(i)
            titleContents.add(
                ItemBase(
                    order.getString("columnName"),
                    order.getString("columnContent")
                )
            )
        }
    }
}