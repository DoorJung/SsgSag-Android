package com.ssgsag.data.model.login

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: LoginToken
)