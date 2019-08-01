package com.ssgsag.ui.main.calendar

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.category.Category
import com.ssgsag.databinding.FragmentCalendarBinding
import com.ssgsag.databinding.ItemCalSortBinding
import com.ssgsag.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarViewModel>(),
    BaseRecyclerViewAdapter.OnItemClickListener {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar
    override val viewModel: CalendarViewModel by viewModel()

    var position = COUNT_PAGE

    lateinit var calendarPagerAdapter: CalendarPagerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel
        //ui
        setCalendarViewPager()
        setSortTab()
    }

    private fun setSortTab() {
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
    }

    private fun setCalendarViewPager() {
        calendarPagerAdapter = CalendarPagerAdapter(childFragmentManager).apply { setNumOfMonth(COUNT_PAGE) }

        viewDataBinding.fragCalTvDay.text = calendarPagerAdapter.getMonthDisplayed(position)

        viewDataBinding.fragCalendarVpPage.run {
            adapter = calendarPagerAdapter
            currentItem = COUNT_PAGE
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    viewDataBinding.fragCalTvDay.text =
                        calendarPagerAdapter.getMonthDisplayed(position)

                    if (position == 0) {
                        calendarPagerAdapter.addPrev()
                        setCurrentItem(COUNT_PAGE, false)
                    } else if (position == calendarPagerAdapter.count - 1) {
                        calendarPagerAdapter.addNext()
                        setCurrentItem(calendarPagerAdapter.count - (COUNT_PAGE + 1), false)
                    }
                    this@CalendarFragment.position = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
    }

    override fun onItemClicked(item: Any?) =
        viewModel.checkCate((item as Category).categoryIdx)

    companion object {
        private val TAG = "CalendarFragment"
        private val COUNT_PAGE = 60
    }
}

