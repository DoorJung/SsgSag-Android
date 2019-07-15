package com.ssgsag.data.model.poster

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single

class PosterRepositoryImpl(val api: NetworkService, val pref: PreferenceManager): PosterRepository {
    override fun getAllPosters(): Single<ArrayList<PosterDetail>> = api
        .posterResponse(pref.findPreference("TOKEN", ""))
        .map { it.data.posters }

    override fun likePoster(posterIdx: Int, like: Int): Single<Int> = api
        .posterLikeResponse(pref.findPreference("TOKEN", ""), posterIdx, like)
        .map { it.status }

    override fun getPoster(posterIdx: Int): Single<PosterDetail> =  api
        .posterDetailResponse(pref.findPreference("TOKEN", ""), posterIdx )
        .map { it.data }
}