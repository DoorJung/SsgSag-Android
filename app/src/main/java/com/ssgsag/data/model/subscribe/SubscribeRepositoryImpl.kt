package com.ssgsag.data.model.subscribe

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.remote.api.NetworkService
import io.reactivex.Single

class SubscribeRepositoryImpl(val api: NetworkService, val pref: PreferenceManager) : SubscribeRepository {
    override fun getSubscribe(): Single<ArrayList<Subscribe>> = api
        .getSubscribeResponse(pref.findPreference("TOKEN", ""))
        .map { it.data }

    override fun subscribe(interestIdx: Int): Single<Int> = api
        .subscribeResponse(pref.findPreference("TOKEN", ""), interestIdx)
        .map { it.status }

    override fun unsubscribe(interestIdx: Int): Single<Int> = api
        .unsubscribeResponse(pref.findPreference("TOKEN", ""), interestIdx)
        .map { it.status }
}