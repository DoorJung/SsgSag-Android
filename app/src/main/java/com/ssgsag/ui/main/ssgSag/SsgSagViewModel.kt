package com.ssgsag.ui.main.ssgSag

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.poster.PosterRepository
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.util.scheduler.SchedulerProvider

class SsgSagViewModel(
    private val repository: PosterRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress
    private val _allPosters = MutableLiveData<ArrayList<PosterDetail>>()
    val allPosters: LiveData<ArrayList<PosterDetail>> get() = _allPosters

    init {
        getAllPosters()
    }

    fun getAllPosters() {
        addDisposable(repository.getAllPosters()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                it.run {
                    _allPosters.postValue(this)
                }
            }, {

            })
        )
    }

    fun selectIsLike(posterIdx: Int, like: Int) {
        addDisposable(
            repository.likePoster(posterIdx, like)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({

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
        private val TAG = "SsgSagViewModel"
    }
}