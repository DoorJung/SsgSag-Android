package com.ssgsag.ui.main.calendar.calendarPage

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.calendar.Calendar
import com.ssgsag.data.model.calendar.CalendarRepository
import com.ssgsag.util.scheduler.SchedulerProvider
import kotlin.collections.ArrayList

class CalendarPageViewModel(
    private val repository: CalendarRepository
    , private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress

    private val _calendar = MutableLiveData<ArrayList<Calendar>>()
    val calendar: LiveData<ArrayList<Calendar>> get() = _calendar

    val calendarByDate = ArrayList<Calendar>()

    init {
        getAllCalendar(true)
    }

    private fun getAllCalendar(isInit: Boolean) {
        if(isInit) {
            addDisposable(repository.getAllCalendar()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe({
                    it.run {
                        _calendar.postValue(this)
                    }
                }, {

                })
            )
        }
    }

    fun getCalendar(year: String, month: String): ArrayList<Calendar> {
        calendarByDate.clear()

        calendar.value?.let {
            for (i in it.indices) {
                if (it[i].posterEndDate.substring(0, 4) == year
                    && it[i].posterEndDate.substring(5, 7) == month
                ) {
                    calendarByDate.add(it[i])
                }
            }
        }
        return calendarByDate
    }

    private fun showProgress() {
        _isProgress.value = View.VISIBLE
    }

    private fun hideProgress() {
        _isProgress.value = View.INVISIBLE
    }

    companion object {
        private val TAG = "CalendarPageViewModel"
    }
}