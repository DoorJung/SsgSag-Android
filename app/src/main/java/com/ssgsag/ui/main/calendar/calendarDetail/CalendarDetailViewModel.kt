package com.ssgsag.ui.main.calendar.calendarDetail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.posterDetail.PosterDetail
import com.ssgsag.data.model.posterDetail.PosterDetailRepository
import com.ssgsag.util.scheduler.SchedulerProvider

class CalendarDetailViewModel(
    private val repository: PosterDetailRepository,
    private val schedulerProvider: SchedulerProvider
    ) : BaseViewModel() {

        private val _isProgress = MutableLiveData<Int>()
        val isProgress: LiveData<Int> get() = _isProgress
        private val _posterDetail = MutableLiveData<PosterDetail>()
        val posterDetail: LiveData<PosterDetail> get() = _posterDetail

        fun getPosterDetail(posterIdx: Int) {
            addDisposable(repository.getPoster(posterIdx)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe({
                    it.run {
                        _posterDetail.postValue(this)
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
        private val TAG = "CalendarDetailViewModel"
    }
}