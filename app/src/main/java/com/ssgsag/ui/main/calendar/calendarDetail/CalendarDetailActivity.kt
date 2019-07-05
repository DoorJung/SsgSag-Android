package com.ssgsag.ui.main.calendar.calendarDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseActivity
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.item.ItemBase
import com.ssgsag.databinding.ActivityCalendarDetailBinding
import com.ssgsag.databinding.ItemPosterDetailBinding
import com.ssgsag.databinding.ItemSsgsagDetailBinding
import com.ssgsag.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarDetailActivity : BaseActivity<ActivityCalendarDetailBinding, CalendarDetailViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_calendar_detail
    override val viewModel: CalendarDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel

        //Toolbar
        setSupportActionBar(viewDataBinding.actCalDetailTb)
        supportActionBar!!.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        viewModel.getPosterDetail(857)

        setRecyclerViewAdapter()

    }

    private fun setRecyclerViewAdapter() {
        //RecyclerView
        viewDataBinding.actCalDetailRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<ItemBase, ItemPosterDetailBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_poster_detail
                    override val bindingVariableId: Int
                        get() = BR.posterDetail
                    override val listener: OnItemClickListener?
                        get() = null
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}