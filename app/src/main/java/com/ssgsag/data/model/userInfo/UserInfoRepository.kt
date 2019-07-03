package com.ssgsag.data.model.userInfo

import io.reactivex.Single

interface UserInfoRepository {
    fun getUserInfo(): Single<UserInfo>
}