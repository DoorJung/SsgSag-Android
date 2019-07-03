package com.ssgsag.data.model.poster

import com.ssgsag.data.model.posterDetail.PosterDetail


data class Poster(
    val posters : ArrayList<PosterDetail>,
    val userCnt : Int
)