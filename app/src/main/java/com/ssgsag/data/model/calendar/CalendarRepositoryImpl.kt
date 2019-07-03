package com.ssgsag.data.model.calendar

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.model.date.Date
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single

class CalendarRepositoryImpl(val api: NetworkService, val pref: PreferenceManager): CalendarRepository {
    override fun getAllCalendar(): Single<ArrayList<Calendar>> = api
        .calendarResponse(pref.findPreference("TOKEN", ""), "0000", "00", "00")
        .map { it.data }

    override fun getCalendar(year: String, month: String, date: String): Single<ArrayList<Calendar>> = api
        .calendarResponse(pref.findPreference("TOKEN", ""), year, month, date)
        .map { it.data }
}