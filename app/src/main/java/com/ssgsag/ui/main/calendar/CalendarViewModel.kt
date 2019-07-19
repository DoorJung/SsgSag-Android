package com.ssgsag.ui.main.calendar

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.category.Category
import com.ssgsag.data.model.schedule.Schedule
import com.ssgsag.data.model.schedule.ScheduleRepository
import com.ssgsag.util.scheduler.SchedulerProvider

class CalendarViewModel(
    private val repository: ScheduleRepository
    , private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val calendarByDate = ArrayList<Schedule>()
    private val defaultSet = arrayListOf(
        Category(-2, true),
        Category(-1, false),
        Category(0, true),
        Category(1, true),
        Category(2, true),
        Category(3, true),
        Category(4, true),
        Category(5, true)
    )

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress

    private val _calendar = MutableLiveData<ArrayList<Schedule>>()
    val schedule: LiveData<ArrayList<Schedule>> get() = _calendar

    private val _categorySort = MutableLiveData<ArrayList<Category>>().apply { postValue(defaultSet) }
    val categorySort: LiveData<ArrayList<Category>> get() = _categorySort

    init {
        getAllCalendar(true)
    }

    fun getCalendar(year: String, month: String): ArrayList<Schedule> {
        calendarByDate.clear()

        schedule.value?.let {
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

    //코드 수정 필요 ㅎㅎ;;;
    fun checkCate(categoryIdx: Int) {
        when (categoryIdx) {
            -2 -> {
                if (!defaultSet[0].isChecked) {
                    _categorySort.postValue(defaultSet.apply {
                        for (i in defaultSet.indices) {
                            if (this[i].categoryIdx != -1) this[i].isChecked = true else this[i].isChecked = false
                        }
                    })
                } else {
                    _categorySort.postValue(defaultSet.apply {
                        for (i in defaultSet.indices) {
                            this[i].isChecked = false
                        }
                    })
                }
            }
            -1 -> {
                if (!defaultSet[1].isChecked) {
                    _categorySort.postValue(defaultSet.apply {
                        for (i in defaultSet.indices) {
                            if (this[i].categoryIdx != -1) this[i].isChecked = false else this[i].isChecked = true
                        }
                    })
                } else {
                    _categorySort.postValue(
                        defaultSet.apply {
                            for (i in defaultSet.indices) {
                                this[i].isChecked = false
                            }
                        })
                }
            }
            else -> {
                defaultSet[categoryIdx + 2].isChecked = !defaultSet[categoryIdx + 2].isChecked
                defaultSet[1].isChecked = false

                for (i in 2 until defaultSet.size) {
                    if (!defaultSet[i].isChecked) {
                        defaultSet[0].isChecked = false; break
                    }
                    if (i == defaultSet.size - 1) defaultSet[0].isChecked = true
                }
                _categorySort.postValue(defaultSet)
            }
        }
    }

    private fun getAllCalendar(isInit: Boolean) {
        if (isInit) {
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

    private fun showProgress() {
        _isProgress.value = View.VISIBLE
    }

    private fun hideProgress() {
        _isProgress.value = View.INVISIBLE
    }

    companion object {
        private val TAG = "CalendarViewModel"
    }
}