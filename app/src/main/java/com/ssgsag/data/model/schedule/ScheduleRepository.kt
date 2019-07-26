package com.ssgsag.data.model.schedule

import io.reactivex.Single

interface ScheduleRepository {
    fun getAllCalendar(): Single<ArrayList<Schedule>>
    fun getCalendar(year: String, month: String, date: String): Single<ArrayList<Schedule>>
    fun bookmarkSchedule(posterIdx: Int): Single<Int>
    fun unbookmarkSchedule(posterIdx: Int): Single<Int>
}