package com.ssgsag.ui.main.calendar.calendarDialog.calendarDialogPage

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
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

class CalendarDialogPageFragment : BaseFragment<FragmentCalendarDialogPageBinding, CalendarDialogPageViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_dialog_page
    override val viewModel: CalendarDialogPageViewModel by viewModel()

    private var timeByMillis: Long = 0
    private var year = ""
    private var month = ""
    private var day = ""
    private val calendar = Calendar.getInstance()

    private val onScheduleItemClickListener =
        object : CalendarDialogPageRecyclerViewAdapter.OnScheduleItemClickListener {
            override fun onItemClicked(posterIdx: Int) = viewModel.navigate(posterIdx)

            override fun onBookmarkClicked(posterIdx: Int, isFavorite: Int) {
                viewModel.bookmark(posterIdx, isFavorite, year, month, day)
            }
        }


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


    }

    override fun onResume() {
        super.onResume()
        setSchedule()
        setScheduleRv()
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

    private fun setScheduleRv() {
        viewDataBinding.fragCalendarDialogPageRv.apply {
            viewModel.schedule.observe(this@CalendarDialogPageFragment, Observer { value ->
                if (adapter != null) {
                    (this.adapter as CalendarDialogPageRecyclerViewAdapter).apply {
                        replaceAll(value)
                        notifyItemRangeChanged(0, value.size)

                    }
                } else {
                    adapter =
                        CalendarDialogPageRecyclerViewAdapter(value).apply {
                            setOnScheduleItemClickListener(onScheduleItemClickListener)
                        }
                    (this.itemAnimator as SimpleItemAnimator).run {
                        changeDuration = 0
                        supportsChangeAnimations = false
                    }
                }
            })
        }
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