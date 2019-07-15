package com.ssgsag.data.model.user.userInfo

data class UserInfoResponse(
    val status: Int,
    val message: String,
    val data: UserInfo
)