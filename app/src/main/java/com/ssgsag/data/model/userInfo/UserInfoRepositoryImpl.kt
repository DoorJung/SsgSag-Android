package com.ssgsag.data.model.userInfo

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single

class UserInfoRepositoryImpl(val api: NetworkService, val pref: PreferenceManager): UserInfoRepository {
    override fun getUserInfo(): Single<UserInfo> = api
        .userInfoResponse(pref.findPreference("TOKEN", ""))
        .map { it.data }
}