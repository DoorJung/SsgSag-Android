package com.ssgsag.data.model.schedule

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single

class ScheduleRepositoryImpl(val api: NetworkService, val pref: PreferenceManager): ScheduleRepository {
    override fun getAllCalendar(): Single<ArrayList<Schedule>> = api
        .calendarResponse(pref.findPreference("TOKEN", ""), "0000", "00", "00")
        .map { it.data }

    override fun getCalendar(year: String, month: String, date: String): Single<ArrayList<Schedule>> = api
        .calendarResponse(pref.findPreference("TOKEN", ""), year, month, date)
        .map { it.data }

    override fun bookmarkSchedule(posterIdx: Int): Single<Int> = api
        .bookmarkPoster(pref.findPreference("TOKEN", ""), posterIdx)
        .map { it.status }

    override fun unbookmarkSchedule(posterIdx: Int): Single<Int> = api
        .unbookmarkPoster(pref.findPreference("TOKEN", ""), posterIdx)
        .map { it.status }
}