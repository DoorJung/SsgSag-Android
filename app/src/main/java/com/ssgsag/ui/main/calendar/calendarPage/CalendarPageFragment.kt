package com.ssgsag.ui.main.calendar.calendarPage

import android.os.Bundle
import android.view.View
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.databinding.FragmentCalendarPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import com.ssgsag.data.model.date.Date
import com.ssgsag.databinding.ItemCalendarDateBinding
import com.ssgsag.ui.main.calendar.CalendarFragment
import com.ssgsag.ui.main.calendar.calendarDialog.CalendarDialogFragment
import com.ssgsag.util.view.NonScrollGridLayoutManagerManager

class CalendarPageFragment : BaseFragment<FragmentCalendarPageBinding, CalendarPageViewModel>(),
    BaseRecyclerViewAdapter.OnItemClickListener {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_page
    override val viewModel: CalendarPageViewModel by viewModel()

    private var timeByMillis: Long = 0
    private val dataList: ArrayList<Date> by lazy { ArrayList<Date>() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel
        setCalendar()
    }

    override fun onItemClicked(item: Any?) {

        val dialogFragment = CalendarDialogFragment()
        val args = Bundle()
        args.putStringArrayList("Date", arrayListOf((item as Date).year, item.month, item.date))
        dialogFragment.arguments = args

        dialogFragment.setTargetFragment(this, 0)

        dialogFragment.show(fragmentManager!!, "fragment_dialog_test")
    }

    fun changePage(isPrev: Boolean) {
        if (isPrev)
            (parentFragment as CalendarFragment).viewDataBinding.fragCalendarVpPage.currentItem -= 1
        else
            (parentFragment as CalendarFragment).viewDataBinding.fragCalendarVpPage.currentItem += 1
    }

    private fun setCalendar() {
        setRecyclerView()
        //캘린더 만들기
        val mCalendar = Calendar.getInstance()
        val mPrevCalendar = Calendar.getInstance()
        val mNextCalendar = Calendar.getInstance()
        val mInstanceCal = Calendar.getInstance()
        //날짜 셋팅
        val date = Date(timeByMillis)
        //현재 캘린더의 년, 월, 일
        val year = yearFormat.format(date)
        val month = monthFormat.format(date)
        val day = dayFormat.format(date)
        //오늘의 년, 월, 일
        val toMonth = monthFormat.format(System.currentTimeMillis())
        val toYear = yearFormat.format(System.currentTimeMillis())
        val toDay = dayFormat.format(System.currentTimeMillis())
        //캘린터 설정
        mCalendar.set(year.toInt(), month.toInt() - 1, 1)
        if (month.toInt() - 1 == 1) {
            mPrevCalendar.set(year.toInt() - 1, 12, 1)
            mNextCalendar.set(year.toInt(), month.toInt(), 1)
        } else if (month.toInt() - 1 == 12) {
            mPrevCalendar.set(year.toInt(), month.toInt() - 2, 1)
            mNextCalendar.set(year.toInt() + 1, 1, 1)
        } else {
            mPrevCalendar.set(year.toInt(), month.toInt() - 2, 1)
            mNextCalendar.set(year.toInt(), month.toInt(), 1)
        }
        //캘린더 줄 수
        val lines: Int
        if (mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 28) {
            if (mCalendar.get(Calendar.DAY_OF_WEEK) == 1)
                lines = 4
            else
                lines = 5
        } else if (mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 30) {
            if (mCalendar.get(Calendar.DAY_OF_WEEK) == 7)
                lines = 6
            else
                lines = 5
        } else if (mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 31) {
            if (mCalendar.get(Calendar.DAY_OF_WEEK) == 6 || mCalendar.get(Calendar.DAY_OF_WEEK) == 7)
                lines = 6
            else
                lines = 5
        } else
            lines = 5

        viewModel.calendar.observe(this, androidx.lifecycle.Observer { value ->
            if (dataList.size == 0) {
                dataList.clear()
                val dayNum = mCalendar.get(Calendar.DAY_OF_WEEK)
                val nextDayNum = mNextCalendar.get(Calendar.DAY_OF_WEEK)
                var instanceDayNum: Int
                //저번달
                for (i in mPrevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayNum + 2 until mPrevCalendar.getActualMaximum(
                    Calendar.DAY_OF_MONTH
                ) + 1) {
                    dataList.add(
                        Date(
                            mPrevCalendar.get(Calendar.YEAR).toString(),
                            (mPrevCalendar.get(Calendar.MONTH) + 1).toString(),
                            i.toString(),
                            0,
                            false,
                            false,
                            false,
                            null,
                            lines
                        )
                    )
                }
                //이번달
                for (i in 0 until mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {

                    mInstanceCal.set(year.toInt(), month.toInt() - 1, i + 1)
                    instanceDayNum = mInstanceCal.get(Calendar.DAY_OF_WEEK)

                    val scheduleList = ArrayList<com.ssgsag.data.model.calendar.Calendar>()
                    val instanceScheduleList = viewModel.getCalendar(year, month)

                    for (j in instanceScheduleList.indices) {
                        if (instanceScheduleList[j].posterEndDate.substring(8, 10).toInt() == i + 1) {
                            scheduleList.add(instanceScheduleList[j])
                        }
                    }

                    if (i + 1 == toDay.toInt() && month == toMonth && year == toYear) {
                        dataList.add(
                            Date(
                                year.toString(),
                                month.toString(),
                                (i + 1).toString(),
                                instanceDayNum,
                                true,
                                true,
                                false,
                                scheduleList,
                                lines
                            )
                        )
                    } else {
                        dataList.add(
                            Date(
                                year.toString(),
                                month.toString(),
                                (i + 1).toString(),
                                instanceDayNum,
                                false,
                                true,
                                false,
                                scheduleList,
                                lines
                            )
                        )
                    }
                }
                //다음달
                if (nextDayNum != 1) {
                    for (i in 1 until 9 - nextDayNum) {
                        dataList.add(
                            Date(
                                mNextCalendar.get(Calendar.YEAR).toString(),
                                (mNextCalendar.get(Calendar.MONTH) + 1).toString(),
                                i.toString(),
                                0,
                                false,
                                false,
                                false,
                                null,
                                lines
                            )
                        )
                    }
                }
                setRecyclerView()
            }
        })

    }

    private fun setRecyclerView() {

        viewDataBinding.fragCalendarPageRv.run {
            adapter = object : BaseRecyclerViewAdapter<Date, ItemCalendarDateBinding>() {
                override val layoutResID: Int
                    get() = R.layout.item_calendar_date
                override val bindingVariableId: Int
                    get() = BR.date
                override val listener: OnItemClickListener?
                    get() = this@CalendarPageFragment
            }
            layoutManager = NonScrollGridLayoutManagerManager(activity!!, 7)
            (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.run {
                replaceAll(dataList)
                notifyDataSetChanged()
            }
        }
    }

    fun setTimeByMillis(timeByMillis: Long) {
        this.timeByMillis = timeByMillis
    }

    interface OnFragmentListener {
        fun onFragmentListener(view: View?)
    }

    companion object {
        val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
        val monthFormat = SimpleDateFormat("MM", Locale.KOREA)
        val dayFormat = SimpleDateFormat("dd", Locale.KOREA)

        private val TAG = "CalendarPageFragment"

        fun newInstance(position: Int): CalendarPageFragment {
            val frg = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            frg.arguments = bundle
            return frg
        }
    }
}

