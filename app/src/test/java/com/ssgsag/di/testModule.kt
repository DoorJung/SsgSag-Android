package com.ssgsag.di

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.ssgsag.util.scheduler.SchedulerProvider
import com.ssgsag.util.TestSchedulerProvider
import org.koin.dsl.module

val testRxModule = module {
    //provide schedule provider
    factory<SchedulerProvider> { TestSchedulerProvider() }
}

val contextModule = module {
    //provide schedule provider
    single { ApplicationProvider.getApplicationContext<Context>() }
}

val testAppModule = listOf(testRxModule, networkModule, localModule, factoryModule, viewModule)