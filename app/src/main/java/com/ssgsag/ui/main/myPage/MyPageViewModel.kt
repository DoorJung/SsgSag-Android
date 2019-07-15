package com.ssgsag.ui.main.myPage

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.user.userInfo.UserInfo
import com.ssgsag.data.model.user.UserRepository
import com.ssgsag.util.scheduler.SchedulerProvider
import android.os.Bundle
import android.util.Log
import com.ssgsag.ui.main.myPage.subscribe.SubscribeActivity
import okhttp3.MediaType
import okhttp3.RequestBody
import kotlin.reflect.KClass


class MyPageViewModel(
    private val repository: UserRepository
    , private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress
    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo
    private val _activityToStart = MutableLiveData<Pair<KClass<*>, Bundle?>>()
    val activityToStart: LiveData<Pair<KClass<*>, Bundle?>> get() = _activityToStart

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        addDisposable(repository.getUserInfo()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                it.run {
                    _userInfo.postValue(this)
                }
            }, {

            })
        )
    }

    fun navigate(idx: Int) {
        when (idx) {
            1 -> _activityToStart.postValue(Pair(SubscribeActivity::class, null))
            else ->
                Log.d(TAG, idx.toString())
        }
    }

    private fun showProgress() {
        _isProgress.value = View.VISIBLE
    }

    private fun hideProgress() {
        _isProgress.value = View.INVISIBLE
    }

    companion object {
        private val TAG = "MyPageViewModel"
    }
}