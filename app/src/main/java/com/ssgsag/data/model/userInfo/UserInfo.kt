package com.ssgsag.data.model.userInfo

data class UserInfo(
    val userIdx: Int,
    val userEmail : String,
    val userName : String,
    val userBirth : String,
    val userNickname : String,
    val userUniv : String,
    val userMajor : String,
    val userGrade : Int,
    val userStudentNum : String,
    val userGender : String,
    val userSignOutDate : String,
    val userSignInDate : String,
    val userPushAllow : Int,
    val userIsSeeker : Int,
    val userCnt : Int,
    val userInfoAllow : Int,
    val userProfileUrl : String,
    val userAlreadyOut : Int,
    val lastLoginTime : String,
    val signupType : Int,
    val userId : String
)