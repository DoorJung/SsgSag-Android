package com.ssgsag.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ssgsag.R
import com.ssgsag.base.BaseActivity
import com.ssgsag.base.BasePagerAdapter
import com.ssgsag.data.local.pref.PreferenceManager
import com.ssgsag.databinding.ActivityMainBinding
import com.ssgsag.ui.main.calendar.CalendarFragment
import com.ssgsag.ui.main.calendar.calendarDetail.CalendarDetailActivity
import com.ssgsag.ui.main.myPage.MyPageFragment
import com.ssgsag.ui.main.ssgSag.SsgSagFragment
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel

        //테스트용 토큰 설정
        PreferenceManager(this).putPreference("TOKEN", getString(R.string.TOKEN))

        //ui
        setViewPager()
        setTabLayout()
    }

    private fun setViewPager() {
        //ViewPager
        viewDataBinding.actMainVp.run {
            adapter = BasePagerAdapter(supportFragmentManager).apply {
                addFragment(MyPageFragment())
                addFragment(SsgSagFragment())
                addFragment(CalendarFragment())
            }
            currentItem = 1
            offscreenPageLimit = 2
        }
    }

    private fun setTabLayout() {
        //TabLayout
        viewDataBinding.actMainTl.run {
            //Customizing tab icon
            val bottomNavigationLayout: View =
                LayoutInflater.from(context).inflate(R.layout.main_navigation_bar, null, false)//inflate뷰를 붙여줌
            setupWithViewPager(viewDataBinding.actMainVp)
            getTabAt(0)!!.customView =
                bottomNavigationLayout.findViewById(R.id.top_navigation_rl_my_page) as RelativeLayout
            getTabAt(1)!!.customView =
                bottomNavigationLayout.findViewById(R.id.top_navigation_rl_ssg_sag) as RelativeLayout
            getTabAt(2)!!.customView =
                bottomNavigationLayout.findViewById(R.id.top_navigation_rl_calendar) as RelativeLayout
        }
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
