package com.ssgsag.data.model.base

data class StringResponse(
    val status: Int,
    val message: String,
    val data: String
)