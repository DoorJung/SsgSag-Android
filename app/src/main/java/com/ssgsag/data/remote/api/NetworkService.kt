package com.ssgsag.data.remote.api

import com.google.gson.JsonObject
import com.ssgsag.SsgSagApplication.Companion.globalApplication
import com.ssgsag.R
import com.ssgsag.data.model.base.IntResponse
import com.ssgsag.data.model.base.NullDataResponse
import com.ssgsag.data.model.schedule.ScheduleResponse
import com.ssgsag.data.model.poster.PosterResponse
import com.ssgsag.data.model.poster.posterDetail.PosterDetailResponse
import com.ssgsag.data.model.subscribe.SubscribeResponse
import com.ssgsag.data.model.user.userInfo.UserInfoResponse
import io.reactivex.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetworkService {

    //region 로그인
    //로그인
    //자동 로그인
    //endregion

    //region 회원
    //이메일 중복확인
    //회원 가입
    @GET("/user")
    fun signUpResponse(
        @Header("Content-Type") content_type : String
    ):  Single<NullDataResponse>
    //회원 조회
    @GET("/user")
    fun userInfoResponse(
        @Header("Authorization") token : String
    ):  Single<UserInfoResponse>
    //회원 관심분야 및 관심직무 조회
    //회원 관심직무 재등록
    //회원 관심분야 재등록
    //회원 정보 수정
    @Multipart
    @POST("/user/update")
    fun editUserInfoResponse(
        @Header("Authorization") token: String,
        @Part("userNickname") userNickname: RequestBody,
        @Part("userUniv") userUniv: RequestBody,
        @Part("userMajor") userMajor: RequestBody,
        @Part("userStudentNum") userStudentNum: RequestBody,
        @Part("userGrade") userGrade: RequestBody,
        @Part("userProfileUrl") userProfileUrl: RequestBody,
        @Part profile: MultipartBody.Part?
    ): Single<NullDataResponse>
    //회원 탈퇴
    //마이페이지 사진등록
    //구독 조회
    @GET("/user/subscribe")
    fun getSubscribeResponse(
        @Header("Authorization") token : String
    ): Single<SubscribeResponse>
    //구독 등록
    @POST("/user/subscribe/{interestIdx}")
    fun subscribeResponse(
        @Header("Authorization") token : String,
        @Path("interestIdx") interestIdx : Int
    ): Single<NullDataResponse>
    //구독 취소
    @DELETE("/user/subscribe/{interestIdx}")
    fun unsubscribeResponse(
        @Header("Authorization") token : String,
        @Path("interestIdx") interestIdx : Int
    ): Single<NullDataResponse>
   //endregion

    //region 포스터
    //포스터 조회
    @GET("/poster")
    fun posterResponse(
        @Header("Authorization") token: String
    ): Single<PosterResponse>
    //포스터 상세 정보 조회
    @GET("/poster/{posterIdx}")
    fun posterDetailResponse(
        @Header("Authorization") token: String,
        @Path("posterIdx") posterIdx : Int
    ): Single<PosterDetailResponse>
    //포스터 좋아요/싫어요
    @POST("/poster/like")
    fun posterLikeResponse(
        @Header("Authorization") token: String,
        @Query("posterIdx") posterIdx: Int,
        @Query("like") like: Int
    ): Single<IntResponse>
    //endregion

    //region 캘린더
    //일정 조회
    @GET("/todo")
    fun calendarResponse(
        @Header("Authorization") token: String,
        @Query("year") year: String,
        @Query("month") month: String,
        @Query("day") day: String
    ): Single<ScheduleResponse>
    //일정 지원 완료
    //일정 삭제
    //일정 지원 완료 조회
    //일정 지원 즐겨찾기
    //endregion

    //region 이력
    //이력 조회
    //이력 추가
    //이력 수정
    //이력 삭제
    //endregion

    //region 공지사항
    //공지사항 조회
    //endregion

    //region 투두리스트 클릭 기록
    //투두리스트 클릭 기록
    //endregion

    //region 업데이트
    //업데이트 확인
    //endregion

    //region 댓글
    //댓글 추가
    @POST("/comment")
    fun writeComment(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body() body: JsonObject
    ): Single<NullDataResponse>
    //댓글 수정
    @PUT("/comment")
    fun editComment(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body() body: JsonObject
    ): Single<NullDataResponse>
    //댓글 삭제
    @DELETE("/comment/{commentIdx}")
    fun deleteComment(
        @Header("Authorization") token: String,
        @Path("commentIdx") commentIdx : Int
    ): Single<NullDataResponse>
    //댓글 좋아요 또는 좋아요 취소
    @POST("/comment/like/{commentIdx}/{like}")
    fun likeComment(
        @Header("Authorization") token: String,
        @Path("commentIdx") commentIdx : Int,
        @Path("like") like : Int
    ): Single<NullDataResponse>
    //endregion

    companion object {
        fun create(): NetworkService {
            return Retrofit.Builder()
                .baseUrl(globalApplication.getString(R.string.TEST_URL))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService::class.java)
        }
    }
}