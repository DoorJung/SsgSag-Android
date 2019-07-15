package com.ssgsag.ui.main.myPage

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.databinding.FragmentMyPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by viewModel()

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

    companion object {
        private val TAG = "MyPageFragment"
    }

}