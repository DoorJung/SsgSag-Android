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
import com.ssgsag.data.model.poster.posterDetail.comment.Comment
import com.ssgsag.databinding.ActivityCalendarDetailBinding
import com.ssgsag.databinding.ItemPosterDetailBinding
import com.ssgsag.ui.main.calendar.calendarDetail.CommentRecyclerViewAdapter.OnCommentItemClickListener
import com.ssgsag.util.view.NonScrollLinearLayoutManager
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarDetailActivity : BaseActivity<ActivityCalendarDetailBinding, CalendarDetailViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_calendar_detail
    override val viewModel: CalendarDetailViewModel by viewModel()
    //0: 아무행동 안함, 1: 댓글 쓰기, 2: 댓글 수정, 3: 댓글 삭제, 4: 댓글 추천
    private var commentBehaviorNum = 0
    private var commentToEdit: Comment? = null

    private val onCommentItemClickListener = object : OnCommentItemClickListener {
        override fun onEditClicked(commentIdx: Int, position: Int) {
            commentBehaviorNum = 2
            commentToEdit = viewModel.posterDetail.value?.commentList!![position]
            viewDataBinding.actCalDetailEtComment.run {
                setText(commentToEdit?.commentContent)
                setSelection(text.toString().length)
            }
            viewDataBinding.actCalDetailEtComment.post(Runnable {
                viewDataBinding.actCalDetailEtComment.setFocusableInTouchMode(true)
                viewDataBinding.actCalDetailEtComment.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(viewDataBinding.actCalDetailEtComment, 0)
            })
        }

        override fun onDeleteClicked(commentIdx: Int, position: Int) {
            commentBehaviorNum = 3
            viewModel.deleteComment(commentIdx, posterIdx)
        }

        override fun onLikeClicked(commentIdx: Int, like: Int) {
            if (like == 0)
                viewModel.likeComment(commentIdx, 1, posterIdx)
            else
                viewModel.likeComment(commentIdx, 0, posterIdx)
        }

        override fun onDeclareClicked(commentIdx: Int) {
            viewModel.cautionComment(commentIdx, posterIdx)
        }
    }

    private var posterIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel

        //getIdx
        posterIdx = intent.getIntExtra("Idx", 0)
        viewModel.getPosterDetail(posterIdx)
        //ui
        setToolbar()
        setPosterDetailRv()
        setCommentRv()
        writeComment()
        bookmarkPoster()
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
            layoutManager = NonScrollLinearLayoutManager(this@CalendarDetailActivity)
        }
    }

    private fun setCommentRv() {
        viewDataBinding.actCalDetailRvComment.apply {
            viewModel.posterDetail.observe(this@CalendarDetailActivity, Observer { value ->
                if (adapter != null) {
                    (this.adapter as CommentRecyclerViewAdapter).apply {
                        replaceAll(value.commentList)
                        if (commentBehaviorNum == 3) {
                            notifyDataSetChanged()
                            commentBehaviorNum = 0
                        } else {
                            notifyItemRangeChanged(0, value.commentList.size)
                        }
                    }
                    when (commentBehaviorNum) {
                        1 -> {
                            viewDataBinding.actCalDetailNsv.run {
                                post(Runnable { fullScroll(View.FOCUS_DOWN) })
                            }
                            viewDataBinding.actCalDetailEtComment.post(Runnable {
                                viewDataBinding.actCalDetailEtComment.setFocusableInTouchMode(true)
                                viewDataBinding.actCalDetailEtComment.requestFocus()
                                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.showSoftInput(viewDataBinding.actCalDetailEtComment, 0)
                            })
                        }
                        2 -> {
                            viewDataBinding.actCalDetailEtComment.post(Runnable {
                                viewDataBinding.actCalDetailEtComment.setFocusableInTouchMode(true)
                                viewDataBinding.actCalDetailEtComment.requestFocus()
                                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.showSoftInput(viewDataBinding.actCalDetailEtComment, 0)
                            })
                        }
                    }
                    commentBehaviorNum = 0
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
            if (viewDataBinding.actCalDetailEtComment.text.toString().isNotEmpty()) {
                when (commentBehaviorNum) {
                    0 -> {
                        commentBehaviorNum = 1
                        viewModel.writeComment(viewDataBinding.actCalDetailEtComment.text.toString(), posterIdx)
                    }
                    2 -> {
                        commentToEdit?.let {
                            viewModel.editComment(
                                viewDataBinding.actCalDetailEtComment.text.toString(),
                                it.commentIdx,
                                posterIdx
                            )
                        }
                    }
                }
                viewDataBinding.actCalDetailEtComment.setText("")
            } else {
                toast("댓글을 입력해주세요.")
            }
        }
    }

    private fun bookmarkPoster() {
        viewDataBinding.actCalDetailIvBookmark.setOnClickListener {
            viewModel.bookmark(posterIdx)
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
