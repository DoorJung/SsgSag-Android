package com.ssgsag.ui.main.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.databinding.FragmentCalendarBinding
import com.ssgsag.ui.main.MainActivity
import com.ssgsag.ui.main.calendar.calendarPage.CalendarPageFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar
    override val viewModel: CalendarViewModel by viewModel()

    var isInited = false
    var position = COUNT_PAGE

    lateinit var calendarPagerAdapter: CalendarPagerAdapter

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isInited) {
            (activity as MainActivity).viewDataBinding.actMainTvTitle.text =
                calendarPagerAdapter.getMonthDisplayed(position)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel

        calendarPagerAdapter = CalendarPagerAdapter(childFragmentManager).apply {
            setNumOfMonth(COUNT_PAGE)
        }

        viewDataBinding.fragCalendarVpPage.run {
            adapter = calendarPagerAdapter
            currentItem = COUNT_PAGE
            offscreenPageLimit = 2
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    (activity as MainActivity).viewDataBinding.actMainTvTitle.text =
                        calendarPagerAdapter.getMonthDisplayed(position)

                    this@CalendarFragment.position = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
        viewDataBinding.fragCalendarTlTag.run {
            addTab(this.newTab().setText("전체"))
            addTab(this.newTab().setText("즐겨찾기"))
            addTab(this.newTab().setText("공모전"))
            addTab(this.newTab().setText("대외활동"))
            addTab(this.newTab().setText("동아리"))
            addTab(this.newTab().setText("인턴"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val position = tab.position
                    when (position) {
                        0 -> {

                        }
                        1 -> {

                        }
                        2 -> {

                        }
                        3 -> {

                        }
                        4 -> {

                        }
                        5 -> {

                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        isInited = true
    }

    companion object {
        private val TAG = "CalendarFragment"
        private val COUNT_PAGE = 60
    }

}

