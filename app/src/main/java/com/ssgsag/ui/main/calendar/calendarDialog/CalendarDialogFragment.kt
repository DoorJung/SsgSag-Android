package com.ssgsag.ui.main.calendar.calendarDialog

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ssgsag.R
import com.ssgsag.base.BaseDialogFragment
import com.ssgsag.databinding.DialogFragmentCalendarBinding
import com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage.CalendarDialogPageFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CalendarDialogFragment : BaseDialogFragment<DialogFragmentCalendarBinding, CalendarDialogViewModel>() {
    override val layoutResID: Int
        get() = R.layout.dialog_fragment_calendar
    override val viewModel: CalendarDialogViewModel by viewModel()

    var position = 12

    var year = 0
    var month = 0
    var date = 0

    lateinit var calendarDialogPagerAdapter: CalendarDialogPagerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel

        //data 불러오기
        val mArgs = arguments
        val mValue = mArgs!!.getStringArrayList("Date")

        year = mValue!![0].toInt()
        month = mValue[1].toInt() - 1
        date = mValue[2].toInt()

        //Schedule 설정
        val cal = Calendar.getInstance()
        val instanceCal = Calendar.getInstance()
        cal.set(year, month, date)

        var dayOfMonth = 0
        dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        //영역 밖을 터치시 dialog dismiss
        viewDataBinding.dialogFragCalendarV.setOnClickListener {
            this.dismiss()
        }

        //PagerAdapter 설정
        calendarDialogPagerAdapter = CalendarDialogPagerAdapter(childFragmentManager).apply {
            setNumOfDay(cal)
        }

        //adapter 적용 및 position에 따른 addNext, addPrev 적용
        viewDataBinding.dialogFragCalendarVp.run {
            adapter = calendarDialogPagerAdapter
            if (date == 1) {
                month -= 1
                instanceCal.set(year, month, 1)
                dayOfMonth = instanceCal.getActualMaximum(Calendar.DAY_OF_MONTH)
                calendarDialogPagerAdapter.addPrev(instanceCal)

                setCurrentItem(dayOfMonth, false)
            } else if (date == dayOfMonth) {
                month += 1
                instanceCal.set(year, month, 1)
                calendarDialogPagerAdapter.addNext(instanceCal)
                setCurrentItem(dayOfMonth - 1, false)
            } else {
                currentItem = date - 1
            }

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        month -= 1
                        instanceCal.set(year, month, 1)
                        dayOfMonth = instanceCal.getActualMaximum(Calendar.DAY_OF_MONTH)
                        calendarDialogPagerAdapter.addPrev(instanceCal)
                        setCurrentItem(dayOfMonth, false)
                    } else if (position == calendarDialogPagerAdapter.count - 1) {
                        month += 1
                        instanceCal.set(year, month, 1)
                        dayOfMonth = instanceCal.getActualMaximum(Calendar.DAY_OF_MONTH)
                        calendarDialogPagerAdapter.addNext(cal)
                        setCurrentItem(calendarDialogPagerAdapter.count - (dayOfMonth + 1), false)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
    }

    companion object {
        private val TAG = "CalendarDialogFragment"
        //고민중..
//        (targetFragment as CalendarPageFragment).changePage(true)
//        (targetFragment as CalendarPageFragment).changePage(false)
    }
}