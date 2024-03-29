package com.ssgsag.ui.main.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.ssgsag.ui.main.calendar.calendarPage.CalendarPageFragment
import java.text.SimpleDateFormat
import java.util.*

class CalendarPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val frgMap: HashMap<Int, CalendarPageFragment>
    private val listMonthByMillis = ArrayList<Long>()
    private var numOfMonth: Int = 0

    init {
        clearPrevFragments(fm)
        frgMap = HashMap()
    }

    private fun clearPrevFragments(fm: FragmentManager) {
        val listFragment = fm.fragments

        if (listFragment != null) {
            val ft = fm.beginTransaction()

            for (f in listFragment) {
                if (f is CalendarPageFragment) {
                    ft.remove(f)
                }
            }
            ft.commitAllowingStateLoss()
        }
    }

    override fun getItem(position: Int): Fragment {
        var frg: CalendarPageFragment? = null
        if (frgMap.size > 0) {
            frg = frgMap[position]
        }
        if (frg == null) {
            frg = CalendarPageFragment.newInstance(position)
            frgMap[position] = frg;
        }
        frg.setTimeByMillis(listMonthByMillis[position])

        return frg
    }

    override fun getCount(): Int {
        return listMonthByMillis.size
    }

    fun setNumOfMonth(numOfMonth: Int) {
        this.numOfMonth = numOfMonth

        val calendar = Calendar.getInstance()
        ///< 12달 전
        calendar.add(Calendar.MONTH, -numOfMonth)
        calendar.set(Calendar.DATE, 1)

        for (i in 0 until numOfMonth * 2 + 1) {
            listMonthByMillis.add(calendar.timeInMillis)
            calendar.add(Calendar.MONTH, 1)
        }
        notifyDataSetChanged()
    }

    fun addNext() {
        val lastMonthMillis = listMonthByMillis[listMonthByMillis.size - 1]

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastMonthMillis
        for (i in 0 until numOfMonth) {
            calendar.add(Calendar.MONTH, 1)
            listMonthByMillis.add(calendar.timeInMillis)
        }

        notifyDataSetChanged()
    }

    fun addPrev() {
        val lastMonthMillis = listMonthByMillis[0]

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastMonthMillis
        calendar.set(Calendar.DATE, 1)
        for (i in numOfMonth downTo 1) {
            calendar.add(Calendar.MONTH, -1)

            listMonthByMillis.add(0, calendar.timeInMillis)
        }

        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun getMonthDisplayed(position: Int): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = listMonthByMillis[position]

        val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
        val monthFormat = SimpleDateFormat("MM", Locale.KOREA)

        val date = Date()
        date.time = listMonthByMillis[position]

        val year = yearFormat.format(date).toInt()
        val month = monthFormat.format(date).toInt()

        return "${year}년 ${month}월"
    }

    fun refresh() {
        notifyDataSetChanged()
    }
}