package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.calendar.Calendar
import com.ssgsag.data.model.calendar.CalendarRepository
import com.ssgsag.util.scheduler.SchedulerProvider

class CalendarDialogPageViewModel(
    private val repository: CalendarRepository
    , private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress
    private val _calendar = MutableLiveData<ArrayList<Calendar>>()
    val calendar: LiveData<ArrayList<Calendar>> get() = _calendar

    fun getCalendar(year: String, month: String, date: String) {
        addDisposable(repository.getCalendar(year, month, date)
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
