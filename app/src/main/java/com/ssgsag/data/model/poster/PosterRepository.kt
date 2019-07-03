package com.ssgsag.data.model.poster

import com.ssgsag.data.model.posterDetail.PosterDetail
import io.reactivex.Single

interface PosterRepository {
    fun getAllPosters(): Single<ArrayList<PosterDetail>>
    fun likePoster(posterIdx: Int, like: Int): Single<Int>
}