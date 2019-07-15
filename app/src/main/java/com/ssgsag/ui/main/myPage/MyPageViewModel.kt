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

    fun editUserInfo() {
        val userNickname = RequestBody.create(MediaType.parse("multipart/form-data"), "도어정")
        val userUniv = RequestBody.create(MediaType.parse("multipart/form-data"), "서울과학기술대학교(본교)")
        val userMajor = RequestBody.create(MediaType.parse("multipart/form-data"), "컴퓨터공학과")
        val userStudentNum = RequestBody.create(MediaType.parse("multipart/form-data"), "17101209")
        val userGrade = RequestBody.create(MediaType.parse("multipart/form-data"), "3")
        val userProfileUrl = RequestBody.create(MediaType.parse("multipart/form-data"), "https://s3.ap-northeast-2.amazonaws.com/project-hs/default_profile.png")

        addDisposable(repository.editUserInfo(userNickname, userUniv , userMajor, userStudentNum, userGrade, userProfileUrl, null)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { hideProgress() }
            .subscribe({
                Log.d(TAG, it.toString())
            }, {
                Log.d(TAG, it.toString())
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