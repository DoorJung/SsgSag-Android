package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.schedule.Schedule
import com.ssgsag.data.model.schedule.ScheduleRepository
import com.ssgsag.ui.main.calendar.calendarDetail.CalendarDetailActivity
import com.ssgsag.util.scheduler.SchedulerProvider
import kotlin.reflect.KClass

class CalendarDialogPageViewModel(
    private val repository: ScheduleRepository
    , private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress
    private val _schedule = MutableLiveData<ArrayList<Schedule>>()
    val schedule: LiveData<ArrayList<Schedule>> get() = _schedule
    private val _activityToStart = MutableLiveData<Pair<KClass<*>, Bundle?>>()
    val activityToStart: LiveData<Pair<KClass<*>, Bundle?>> get() = _activityToStart

    fun getSchedule(year: String, month: String, date: String) {
        addDisposable(repository.getCalendar(year, month, date)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                it.run {
                    _schedule.postValue(this)
                }
            }, {

            })
        )
    }

    fun navigate(idx: Int) {
        val bundle = Bundle().apply { putInt("Idx", idx) }
        _activityToStart.postValue(Pair(CalendarDetailActivity::class, bundle))
    }

    private fun showProgress() {
        _isProgress.value = View.VISIBLE
    }

    private fun hideProgress() {
        _isProgress.value = View.INVISIBLE
    }

    companion object {
        private val TAG = "CalendarDialogPageViewModel"
    }
}