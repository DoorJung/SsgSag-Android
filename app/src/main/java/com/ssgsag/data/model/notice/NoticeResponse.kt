package com.ssgsag.data.model.notice

data class NoticeResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<Notice>
)