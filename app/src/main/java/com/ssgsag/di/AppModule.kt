package com.ssgsag.di

import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.model.schedule.ScheduleRepository
import com.ssgsag.data.model.schedule.ScheduleRepositoryImpl
import com.ssgsag.data.model.poster.PosterRepository
import com.ssgsag.data.model.poster.PosterRepositoryImpl
import com.ssgsag.data.model.subscribe.SubscribeRepository
import com.ssgsag.data.model.subscribe.SubscribeRepositoryImpl
import com.ssgsag.data.model.user.UserRepository
import com.ssgsag.data.model.user.UserRepositoryImpl
import com.ssgsag.data.remote.api.NetworkService
import com.ssgsag.ui.main.MainViewModel
import com.ssgsag.ui.main.calendar.CalendarViewModel
import com.ssgsag.ui.main.calendar.calendarDetail.CalendarDetailViewModel
import com.ssgsag.ui.main.calendar.calendarDialog.CalendarDialogViewModel
import com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage.CalendarDialogPageViewModel
import com.ssgsag.ui.main.myPage.MyPageViewModel
import com.ssgsag.ui.main.myPage.subscribe.SubscribeViewModel
import com.ssgsag.ui.main.ssgSag.SsgSagViewModel
import com.ssgsag.util.scheduler.AndroidSchedulerProvider
import com.ssgsag.util.scheduler.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val rxModule = module {
    //provide schedule provider
    factory<SchedulerProvider> { AndroidSchedulerProvider() }
}

val networkModule = module {
    single { NetworkService.create() }
}

val localModule = module {
    //SharedPreference
    single { PreferenceManager(get()) }
    //Room
}

val factoryModule = module {
    factory<SubscribeRepository> {
        SubscribeRepositoryImpl(
            get(), get()
        )
    }
    factory<UserRepository> {
        UserRepositoryImpl(
            get(), get()
        )
    }
    factory<PosterRepository> {
        PosterRepositoryImpl(
            get(), get()
        )
    }
    factory<ScheduleRepository> {
        ScheduleRepositoryImpl(
            get(), get()
        )
    }
}

val viewModule = module {
    //Common
    viewModel { MainViewModel() }
    //MyPage
    viewModel { MyPageViewModel(get(), get()) }
    viewModel { SubscribeViewModel(get(), get()) }
    //SsgSag
    viewModel { SsgSagViewModel(get(), get()) }
    //Schedule
    viewModel { CalendarViewModel(get(), get()) }
    viewModel { CalendarDialogViewModel() }
    viewModel { CalendarDialogPageViewModel(get(), get()) }
    viewModel { CalendarDetailViewModel(get(), get(), get()) }
}

val appModule = listOf(rxModule, networkModule, localModule, factoryModule, viewModule)