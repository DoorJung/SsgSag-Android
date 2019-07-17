package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.databinding.FragmentCalendarDialogPageBinding
import com.ssgsag.databinding.ItemCalendarScheduleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarDialogPageFragment : BaseFragment<FragmentCalendarDialogPageBinding, CalendarDialogPageViewModel>(),
    BaseRecyclerViewAdapter.OnItemClickListener {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_dialog_page
    override val viewModel: CalendarDialogPageViewModel by viewModel()

    private var timeByMillis: Long = 0
    private var isInit = false

    private var year = ""
    private var month = ""
    private var day = ""
    private val calendar = Calendar.getInstance()

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        if (isVisibleToUser && isInit) {
//            if (day == "01" && month.toInt() != (parentFragment as CalendarDialogFragment).month + 2) {
//                ((parentFragment as CalendarDialogFragment).targetFragment as CalendarPageFragment).changePage(false)
//                (parentFragment as CalendarDialogFragment).month += 1
//            } else if (day == calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString() && month.toInt() != (parentFragment as CalendarDialogFragment).month) {
//                ((parentFragment as CalendarDialogFragment).targetFragment as CalendarPageFragment).changePage(true)
//                (parentFragment as CalendarDialogFragment).month -= 1
//            }
//            isInit = false
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel

        viewModel.activityToStart.observe(this, Observer { value ->
            val intent = Intent(activity, value.first.java)
            value.second?.let {
                intent.putExtras(it)
            }
            startActivity(intent)
        })

        calendar.timeInMillis = timeByMillis

        val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
        val monthFormat = SimpleDateFormat("MM", Locale.KOREA)
        val dateFormat = SimpleDateFormat("dd", Locale.KOREA)

        val date = Date()
        date.time = timeByMillis

        year = yearFormat.format(date)
        month = monthFormat.format(date)
        day = dateFormat.format(date)
        var dayOfWeek = "일"
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> dayOfWeek = "일"
            2 -> dayOfWeek = "월"
            3 -> dayOfWeek = "화"
            4 -> dayOfWeek = "수"
            5 -> dayOfWeek = "목"
            6 -> dayOfWeek = "금"
            7 -> dayOfWeek = "토"
        }

        viewDataBinding.fragCalendarDialogPageTvDate.text = "${month.toInt()}월 ${day.toInt()}일 ${dayOfWeek}요일"
        viewModel.getCalendar(year, month, day)

        viewDataBinding.fragCalendarDialogPageRv.adapter =
            object : BaseRecyclerViewAdapter<com.ssgsag.data.model.calendar.Calendar, ItemCalendarScheduleBinding>() {
                override val layoutResID: Int
                    get() = R.layout.item_calendar_schedule
                override val bindingVariableId: Int
                    get() = BR.calendar
                override val listener: OnItemClickListener?
                    get() = this@CalendarDialogPageFragment
            }
        isInit = true
    }

    fun setTimeByMillis(timeByMillis: Long) {
        this.timeByMillis = timeByMillis
    }

    override fun onItemClicked(item: Any?) {
        
    }

    companion object {
        private val TAG = "CalendarDialogPageFragment"

        fun newInstance(position: Int): CalendarDialogPageFragment {
            val frg = CalendarDialogPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            frg.arguments = bundle
            return frg
        }
    }
}