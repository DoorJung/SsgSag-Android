package com.ssgsag.data.model.date

import com.ssgsag.data.model.schedule.Schedule

data class Date(
    var year : String,
    var month : String,
    var date : String,
    var dayOfWeek: Int,
    var isToDay: Boolean,
    var isCurrentMonth: Boolean,
    var isClickDay: Boolean,
    var schedule : ArrayList<Schedule> ? = null,
    var lines: Int
)