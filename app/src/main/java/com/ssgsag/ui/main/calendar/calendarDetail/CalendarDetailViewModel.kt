package com.ssgsag.ui.main.calendar.calendarDetail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.poster.PosterRepository
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.data.model.schedule.ScheduleRepository
import com.ssgsag.util.scheduler.SchedulerProvider
import io.reactivex.Single
import org.json.JSONObject

class CalendarDetailViewModel(
    private val posterRepository: PosterRepository,
    private val scheduleRepository: ScheduleRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress
    private val _posterDetail = MutableLiveData<PosterDetail>()
    val posterDetail: LiveData<PosterDetail> get() = _posterDetail

    fun getPosterDetail(posterIdx: Int) {
        addDisposable(posterRepository.getPoster(posterIdx)
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

    //region comment
    fun writeComment(commentContent: String, posterIdx: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("posterIdx", posterIdx)
        jsonObject.put("commentContent", commentContent)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        addDisposable(posterRepository.writeComment(body)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                getPosterDetail(posterIdx)
            }, {

            })
        )
    }

    fun editComment(commentContent: String, commentIdx: Int, posterIdx: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("posterIdx", commentIdx)
        jsonObject.put("commentContent", commentContent)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        addDisposable(posterRepository.editComment(body)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                getPosterDetail(posterIdx)
            }, {

            })
        )
    }

    fun deleteComment(commentIdx: Int, posterIdx: Int) {
        addDisposable(posterRepository.deleteComment(commentIdx)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                getPosterDetail(posterIdx)
            }, {

            })
        )
    }

    fun likeComment(commentIdx: Int, like: Int, posterIdx: Int) {
        addDisposable(posterRepository.likeComment(commentIdx, like)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                getPosterDetail(posterIdx)
            }, {

            })
        )
    }

    fun cautionComment(commentIdx: Int, posterIdx: Int) {
        addDisposable(posterRepository.cautionComment(commentIdx)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                getPosterDetail(posterIdx)
            }, {

            })
        )
    }
    //endregion



    fun bookmark(posterIdx: Int) {
        lateinit var response: Single<Int>

        if(posterDetail.value?.isFavorite == 0) response = scheduleRepository.bookmarkSchedule(posterIdx)
        else if(posterDetail.value?.isFavorite == 1) response = scheduleRepository.unbookmarkSchedule(posterIdx)

        addDisposable(response
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                getPosterDetail(posterIdx)
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