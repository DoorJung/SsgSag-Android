package com.ssgsag.ui.main.ssgSag

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseFragment
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.subscribe.Subscribe
import com.ssgsag.databinding.FragmentSsgSagBinding
import com.ssgsag.databinding.ItemSubscribeBinding
import com.ssgsag.ui.main.MainActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import org.koin.androidx.viewmodel.ext.android.viewModel

class SsgSagFragment : BaseFragment<FragmentSsgSagBinding, SsgSagViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_ssg_sag
    override val viewModel: SsgSagViewModel by viewModel()

    var position = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel

        //CardStackView
        viewDataBinding.fragSsgSagCv.apply {
            adapter = SsgSagCardStackAdapter(activity!!)
            layoutManager = CardStackLayoutManager(activity, cardStackListener).apply {
                setVisibleCount(3)
                setDirections(Direction.HORIZONTAL)
                setCanScrollHorizontal(true)
                setCanScrollVertical(false)
            }
        }
        viewModel.allPosters.observe(this@SsgSagFragment, Observer { value ->
            (viewDataBinding.fragSsgSagCv.adapter as SsgSagCardStackAdapter).replaceAll(value)
        })
    }

    private val cardStackListener = object : CardStackListener {
        override fun onCardDisappeared(view: View?, position: Int) {}
        override fun onCardDragging(direction: Direction?, ratio: Float) {}
        override fun onCardSwiped(direction: Direction?) {
            if (direction == Direction.Right) {
                viewModel.ssgSag(viewModel.allPosters.value!![position].posterIdx, 1)
            } else if (direction == Direction.Left) {
                viewModel.ssgSag(viewModel.allPosters.value!![position].posterIdx, 0)
            }
            position++
        }

        override fun onCardCanceled() {}
        override fun onCardAppeared(view: View?, position: Int) {}
        override fun onCardRewound() {}
    }

    companion object {
        private val TAG = "SsgSagFragment"
    }
}