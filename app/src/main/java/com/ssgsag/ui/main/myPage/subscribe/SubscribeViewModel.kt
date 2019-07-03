package com.ssgsag.ui.main.myPage.subscribe

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.subscribe.Subscribe
import com.ssgsag.data.model.subscribe.SubscribeRepository
import com.ssgsag.util.scheduler.SchedulerProvider

class SubscribeViewModel(
    private val repository: SubscribeRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress
    private val _subscribe = MutableLiveData<ArrayList<Subscribe>>()
    val subscribe: LiveData<ArrayList<Subscribe>> get() = _subscribe

    init {
        getSubscribe()
    }

    fun getSubscribe() {
        addDisposable(repository.getSubscribe()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                it.run {
                    _subscribe.postValue(this)
                }
            }, {
            })
        )
    }

    fun subscribe(interestIdx: Int, userIdx: Int) {
        if (userIdx == 0) {
            //subscribe
            addDisposable(repository.subscribe(interestIdx)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe({
                    getSubscribe()
                }, {

                })
            )
        } else {
            //unsubscribe
            addDisposable(repository.unsubscribe(interestIdx)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe({
                    getSubscribe()
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
        private val TAG = "SubscribeViewModel"
    }
}