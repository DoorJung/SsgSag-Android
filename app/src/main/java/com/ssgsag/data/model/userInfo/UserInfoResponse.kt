package com.ssgsag.data.model.userInfo

data class UserInfoResponse(
    val status: Int,
    val message: String,
    val data: UserInfo
)