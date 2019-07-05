package com.ssgsag

import android.app.Application
import com.ssgsag.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SsgSagApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        globalApplication = this
        startKoin {
            androidLogger()
            androidContext(this@SsgSagApplication)
            modules(appModule)
        }
    }

    companion object {
        lateinit var globalApplication: SsgSagApplication
        val ContestTitle = arrayListOf("주제", "지원자격", "시상내역")
        val ActTitle = arrayListOf("지원자격", "활동내역", "혜택")
        val ClubTitle = arrayListOf("활동분야", "모임시간", "혜택")
        val RecruitTitle = arrayListOf("모집분야", "지원자격", "근무지역")
    }
}