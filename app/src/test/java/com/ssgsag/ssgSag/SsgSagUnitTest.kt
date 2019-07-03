package com.ssgsag.ssgSag

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ssgsag.base.BaseTest
import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.data.model.posterDetail.PosterDetail
import com.ssgsag.di.testAppModule
import com.ssgsag.ui.main.ssgSag.SsgSagViewModel
import com.ssgsag.util.Settings
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SsgSagUnitTest : BaseTest() {
    private val viewModel: SsgSagViewModel by inject()
    private val pref: PreferenceManager by inject()

//    @Mock
//    private lateinit var listObserver: Observer<ArrayList<PosterDetail>>

//    @Before
//    override fun before() {
//        super.before()
//        MockitoAnnotations.initMocks(this)
//    }

    @Test
    fun test() {
        pref.putPreference("TOKEN", Settings.TOKEN)
        viewModel.getAllPosters()

//        val value = viewModel.allPosters.value ?: error("No value for view myModel")
//
//        Mockito.verify(listObserver).onChanged(value)
    }
}