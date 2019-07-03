package com.ssgsag.data.model.calendar


data class Calendar(
    val posterIdx : Int,
    val categoryIdx : Int,
    val isCompleted : Int,
    val isEnded : Int,
    val posterName : String,
    val outline : String,
    val posterStartDate: String,
    val posterEndDate: String,
    val documentDate: String,
    val isFavorite : Int,
    val photoUrl: String,
    val dday : Int
)