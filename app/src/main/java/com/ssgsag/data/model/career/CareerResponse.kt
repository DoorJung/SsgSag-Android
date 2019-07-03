package com.ssgsag.data.model.career

data class CareerResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<Career>
)