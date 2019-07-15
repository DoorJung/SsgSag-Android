package com.ssgsag.data.model.user

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.model.user.userInfo.UserInfo
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepositoryImpl(val api: NetworkService, val pref: PreferenceManager) :
    UserRepository {

    override fun getUserInfo(): Single<UserInfo> = api
        .userInfoResponse(pref.findPreference("TOKEN", ""))
        .map { it.data }
}