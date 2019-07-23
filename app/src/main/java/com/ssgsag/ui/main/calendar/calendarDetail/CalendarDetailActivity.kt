package com.ssgsag.ui.main.calendar.calendarDetail

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ssgsag.BR
import com.ssgsag.R
import com.ssgsag.base.BaseActivity
import com.ssgsag.base.BaseRecyclerViewAdapter
import com.ssgsag.data.model.item.ItemBase
import com.ssgsag.databinding.ActivityCalendarDetailBinding
import com.ssgsag.databinding.ItemPosterDetailBinding
import com.ssgsag.ui.main.calendar.calendarDetail.CommentRecyclerViewAdapter.OnCommentItemClickListener
import com.ssgsag.util.view.NonScrollLinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarDetailActivity : BaseActivity<ActivityCalendarDetailBinding, CalendarDetailViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_calendar_detail
    override val viewModel: CalendarDetailViewModel by viewModel()
    //0: 아무행동 안함, 1: 댓글 쓰기, 2: 댓글 수정, 3: 댓글 삭제, 4: 댓글 추천
    private var commentBehaviorNum = 0

    private val onCommentItemClickListener = object : OnCommentItemClickListener {
        override fun onEditClicked(commentIdx: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDeleteClicked(commentIdx: Int, position: Int) {
            commentBehaviorNum = 4
            viewModel.deleteComment(commentIdx, posterIdx)
        }

        override fun onLikeClicked(commentIdx: Int, like: Int) {
            if (like == 0)
                viewModel.likeComment(commentIdx, 1, posterIdx)
            else
                viewModel.likeComment(commentIdx, 0, posterIdx)
        }
    }

    private var posterIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewDataBinding.actCalDetailRv.layoutManager = NonScrollLinearLayoutManager(this)

        //ui
        setToolbar()
        setPosterDetailRv()
        setCommentRv()
        writeComment()
        //getIdx
        posterIdx = intent.getIntExtra("Idx", 0)
        viewModel.getPosterDetail(posterIdx)
    }

    private fun setToolbar() {
        setSupportActionBar(viewDataBinding.actCalDetailTb)
        supportActionBar!!.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    private fun setPosterDetailRv() {
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

    private fun setCommentRv() {
        viewDataBinding.actCalDetailRvComment.apply {
            viewModel.posterDetail.observe(this@CalendarDetailActivity, Observer { value ->
                if (adapter != null) {
                    (this.adapter as CommentRecyclerViewAdapter).apply {
                        replaceAll(value.commentList)
                        if (commentBehaviorNum == 4) {
                            notifyDataSetChanged()
                            commentBehaviorNum = 0
                        } else {
                            notifyItemRangeChanged(0, value.commentList.size - 1)
                        }
                    }
                    if (commentBehaviorNum == 1) {
                        viewDataBinding.actCalDetailNsv.run {
                            post(Runnable { fullScroll(View.FOCUS_DOWN) })
                        }
                        viewDataBinding.actCalDetailEtComment.post(Runnable {
                            viewDataBinding.actCalDetailEtComment.setFocusableInTouchMode(true)
                            viewDataBinding.actCalDetailEtComment.requestFocus()
                            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(viewDataBinding.actCalDetailEtComment, 0)
                        })
                        commentBehaviorNum = 0
                    }
                } else {
                    adapter =
                        CommentRecyclerViewAdapter(this@CalendarDetailActivity, value.commentList).apply {
                            setOnCommentItemClickListener(onCommentItemClickListener)
                        }
                    layoutManager = NonScrollLinearLayoutManager(this@CalendarDetailActivity)
                    (this.itemAnimator as SimpleItemAnimator).run {
                        changeDuration = 0
                        supportsChangeAnimations = false
                    }
                }
            })
        }
    }

    private fun writeComment() {
        viewDataBinding.actCalDetailIvCommentWrite.setOnClickListener {
            commentBehaviorNum = 1
            viewModel.writeComment(viewDataBinding.actCalDetailEtComment.text.toString(), posterIdx)
            viewDataBinding.actCalDetailEtComment.setText("")
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
