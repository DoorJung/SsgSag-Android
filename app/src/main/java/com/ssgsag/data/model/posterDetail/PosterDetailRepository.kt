package com.ssgsag.data.model.posterDetail

import io.reactivex.Single

interface PosterDetailRepository {
    fun getPoster(posterIdx: Int): Single<PosterDetail>
}