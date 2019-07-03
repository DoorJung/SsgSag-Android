package com.ssgsag.data.model.subscribe

import com.ssgsag.data.model.base.NullResponse
import io.reactivex.Single

interface SubscribeRepository {
    fun getSubscribe(): Single<ArrayList<Subscribe>>
    fun subscribe(interestIdx: Int): Single<Int>
    fun unsubscribe(interestIdx: Int): Single<Int>
}