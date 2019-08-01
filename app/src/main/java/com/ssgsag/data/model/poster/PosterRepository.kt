package com.ssgsag.data.model.poster

import com.google.gson.JsonObject
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.data.model.poster.posterDetail.comment.Comment
import io.reactivex.Single

interface PosterRepository {
    //poster
    fun getAllPosters(): Single<ArrayList<PosterDetail>>
    fun ssgSag(posterIdx: Int, like: Int): Single<Int>
    fun getPoster(posterIdx: Int): Single<PosterDetail>
    //comment
    fun writeComment(body: JsonObject): Single<Int>
    fun editComment(body: JsonObject): Single<Int>
    fun deleteComment(commentIdx: Int): Single<Int>
    fun likeComment(commentIdx: Int, like: Int): Single<Int>
    fun cautionComment(commentIdx: Int): Single<Int>
}