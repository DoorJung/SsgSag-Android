package com.ssgsag.data.model.posterDetail

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import org.intellij.lang.annotations.Identifier
import java.lang.Exception

data class PosterDetail(
    val posterIdx: Int,
    val categoryIdx: Int,
    val photoUrl: String,
    val posterName: String,
    val posterRegDate: String,
    val posterStartDate: String?,
    val posterEndDate: String,
    val posterWebSite: String,
    val outline: String,
    val target: String,
    val period: String,
    val benefit: String,
    val documentDate: String,
    val contentIdx: Int,
    val hostIdx: Int,
    val posterDetail: String,
    val posterInterest: ArrayList<Int>,
    val dday: String,
    val adminAccept: String,
    val keyword: String
)