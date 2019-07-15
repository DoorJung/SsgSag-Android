package com.ssgsag.data.model.user

import com.ssgsag.data.model.user.userInfo.UserInfo
import io.reactivex.Single

interface UserRepository {
    fun getUserInfo(): Single<UserInfo>
}