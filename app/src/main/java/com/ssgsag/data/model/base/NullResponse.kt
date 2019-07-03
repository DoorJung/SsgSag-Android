package com.ssgsag.data.model.base

data class NullResponse(
    val status: Int,
    val message: String,
    val data: String?
)