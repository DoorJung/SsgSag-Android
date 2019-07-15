package com.ssgsag.data.model.poster

import com.ssgsag.data.model.poster.posterDetail.PosterDetail


data class Poster(
    val posters : ArrayList<PosterDetail>,
    val userCnt : Int
)