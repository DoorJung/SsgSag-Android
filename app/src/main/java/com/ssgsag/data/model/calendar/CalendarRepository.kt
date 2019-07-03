package com.ssgsag.data.model.calendar

import com.ssgsag.data.model.date.Date
import io.reactivex.Single

interface CalendarRepository {
    fun getAllCalendar(): Single<ArrayList<Calendar>>
    fun getCalendar(year: String, month: String, date: String): Single<ArrayList<Calendar>>
}