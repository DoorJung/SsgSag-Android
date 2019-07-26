package com.ssgsag.ui.main.myPage.subscribe

import android.os.Bundle
import android.view.MenuItem
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseActivity
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.subscribe.Subscribe
import com.ssgsag.databinding.ActivitySubscribeBinding
import com.ssgsag.databinding.ItemSubscribeBinding
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscribeActivity : BaseActivity<ActivitySubscribeBinding, SubscribeViewModel>(), BaseRecyclerViewAdapter.OnItemClickListener {

    override val layoutResID: Int
        get() = R.layout.activity_subscribe

    override val viewModel: SubscribeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        //Toolbar
        setSupportActionBar(viewDataBinding.actSubscribeTbToolbar)
        supportActionBar!!.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        setRecyclerViewAdapter()
    }

    private fun setRecyclerViewAdapter() {
        //RecyclerView
        viewDataBinding.actSubscribeRvSubscribe.apply {
            adapter =
                object : BaseRecyclerViewAdapter<Subscribe, ItemSubscribeBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_subscribe
                    override val bindingVariableId: Int
                        get() = BR.subscribe
                    override val listener: OnItemClickListener?
                        get() = this@SubscribeActivity
                }
        }
    }

    override fun onItemClicked(item: Any?) {
        viewModel.subscribe((item as Subscribe).interestIdx, item.userIdx)
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

    companion object {
        private val TAG = "SubscribeActivity"
    }
}