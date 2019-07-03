package com.ssgsag.data.model.posterDetail

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.model.posterDetail.PosterDetail
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single

class PosterDetailRepositoryImpl(val api: NetworkService, val pref: PreferenceManager): PosterDetailRepository {
    override fun getPoster(posterIdx: Int): Single<PosterDetail> =  api
        .posterDetailResponse(pref.findPreference("TOKEN", ""), posterIdx )
        .map { it.data }
}