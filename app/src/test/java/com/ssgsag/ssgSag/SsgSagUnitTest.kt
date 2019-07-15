package com.ssgsag.ssgSag

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ssgsag.base.BaseTest
import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.ui.main.ssgSag.SsgSagViewModel
import com.ssgsag.util.Settings
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject

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