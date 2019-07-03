package com.ssgsag.data.model.calendar

data class CalendarResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<Calendar>
)