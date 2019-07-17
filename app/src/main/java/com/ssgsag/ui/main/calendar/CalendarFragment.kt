package com.ssgsag.ui.main.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.category.Category
import com.ssgsag.databinding.FragmentCalendarBinding
import com.ssgsag.databinding.ItemCalSortBinding
import com.ssgsag.ui.main.MainActivity
import com.ssgsag.ui.main.calendar.calendarPage.CalendarPageFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarViewModel>(),
    BaseRecyclerViewAdapter.OnItemClickListener {

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

        viewDataBinding.fragCalendarRvSort.apply {
            adapter =
                object : BaseRecyclerViewAdapter<Category, ItemCalSortBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_cal_sort
                    override val bindingVariableId: Int
                        get() = BR.category
                    override val listener: OnItemClickListener?
                        get() = this@CalendarFragment
                }
        }

        isInited = true
    }

    override fun onItemClicked(item: Any?) =
        viewModel.checkCate((item as Category).categoryIdx)


    companion object {
        private val TAG = "CalendarFragment"
        private val COUNT_PAGE = 60
    }

}

