package com.ssgsag.data.model.poster.posterDetail

import com.ssgsag.data.model.poster.posterDetail.analytics.Analytics
import com.ssgsag.data.model.poster.posterDetail.comment.Comment

data class PosterDetail(
    val posterIdx: Int,
    val categoryIdx: Int,
    val photoUrl: String,
    val photoUrl2: String?,
    val posterName: String,
    val posterRegDate: String,
    val posterStartDate: String?,
    val posterEndDate: String,
    val posterWebSite: String?,
    val posterWebSite2: String?,
    val outline: String,
    val target: String,
    val period: String,
    val benefit: String,
    val documentDate: String,
    val contentIdx: Int,
    val hostIdx: Int,
    val posterDetail: String,
    val posterInterest: ArrayList<Int>,
    val adminAccept: String,
    val keyword: String,
    val chargerName: String?,
    val partnerPhone: String?,
    val partnerEmail: String?,
    val favoriteNum: Int,
    val likeNum: Int,
    val analytics: Analytics,
    val commentList: ArrayList<Comment>,
    val dday: String
)