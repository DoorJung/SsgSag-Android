package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.schedule.Schedule
import com.ssgsag.databinding.FragmentCalendarDialogPageBinding
import com.ssgsag.databinding.ItemCalendarScheduleBinding
import com.ssgsag.util.DateUtil.dateFormat
import com.ssgsag.util.DateUtil.monthFormat
import com.ssgsag.util.DateUtil.yearFormat
import com.ssgsag.util.extensionFunction.getDayOfWeek
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarDialogPageFragment : BaseFragment<FragmentCalendarDialogPageBinding, CalendarDialogPageViewModel>(),
    BaseRecyclerViewAdapter.OnItemClickListener {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_dialog_page
    override val viewModel: CalendarDialogPageViewModel by viewModel()

    private var timeByMillis: Long = 0
    private var year = ""
    private var month = ""
    private var day = ""
    private val calendar = Calendar.getInstance()

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

        setSchedule()
        setRecyclerView()
    }

    fun setTimeByMillis(timeByMillis: Long) {
        this.timeByMillis = timeByMillis
    }

    private fun setSchedule() {
        calendar.timeInMillis = timeByMillis

        val date = Date()
        date.time = timeByMillis

        year = yearFormat.format(date)
        month = monthFormat.format(date)
        day = dateFormat.format(date)

        var dayOfWeek = ""
        dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))

        viewDataBinding.fragCalendarDialogPageTvDate.text = "${month.toInt()}월 ${day.toInt()}일 ${dayOfWeek}요일"

        viewModel.getSchedule(year, month, day)
    }

    private fun setRecyclerView() {
        viewDataBinding.fragCalendarDialogPageRv.adapter =
            object : BaseRecyclerViewAdapter<Schedule, ItemCalendarScheduleBinding>() {
                override val layoutResID: Int
                    get() = R.layout.item_calendar_schedule
                override val bindingVariableId: Int
                    get() = BR.schedule
                override val listener: OnItemClickListener?
                    get() = this@CalendarDialogPageFragment
            }
    }

    override fun onItemClicked(item: Any?) {
        viewModel.navigate((item as Schedule).posterIdx)
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