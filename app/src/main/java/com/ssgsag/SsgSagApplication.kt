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
    }
}