package com.ssgsag.util.extension

import com.ssgsag.data.model.base.TitleContents
import com.ssgsag.data.model.posterDetail.PosterDetail
import org.json.JSONArray

class PosterExtension {

    companion object {
        val ContestTitle = arrayListOf("주제", "지원자격", "시상내역")
        val ActTitle = arrayListOf("지원자격", "활동내역", "혜택")
        val ClubTitle = arrayListOf("활동분야", "모임시간", "혜택")
        val RecruitTitle = arrayListOf("모집분야", "지원자격", "근무지역")

        fun getTitleContents(poster: PosterDetail?): ArrayList<TitleContents> {
            val list: ArrayList<TitleContents> = ArrayList()
            when (poster?.categoryIdx) {
                0 -> dataInject(list, ContestTitle, poster)
                1 -> dataInject(list, ActTitle, poster)
                2, 6 -> dataInject(list, ClubTitle, poster)
                4 -> dataInject(list, RecruitTitle, poster)
                3, 5, 7 -> dataInject(list, null, poster)
            }
            return list
        }


        fun dataInject(titleContents: ArrayList<TitleContents>, titleByCate: ArrayList<String>?, poster: PosterDetail) {
            titleByCate?.let {
                titleContents.add(TitleContents(it[0], poster.outline))
                titleContents.add(TitleContents(it[1], poster.target))
                titleContents.add(TitleContents(it[2], poster.benefit))
            } ?: run {
                val ja = JSONArray(poster.outline)
                for (i in 0 until ja.length()) {
                    val order = ja.getJSONObject(i)
                    titleContents.add(TitleContents(order.getString("columnName"), order.getString("columnContent")))
                }
            }
        }
    }
}