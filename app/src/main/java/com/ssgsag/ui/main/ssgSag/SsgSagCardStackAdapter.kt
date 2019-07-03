package com.ssgsag.ui.main.ssgSag

import android.content.Context
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssgsag.R
import com.ssgsag.data.model.posterDetail.PosterDetail
import com.ssgsag.databinding.ItemSsgsagBinding



class SsgSagCardStackAdapter(val ctx: Context, val listener: OnItemClickListener?) :
    RecyclerView.Adapter<SsgSagCardStackAdapter.ViewHolder>() {

    val items = ArrayList<PosterDetail>()

    fun replaceAll(items: ArrayList<PosterDetail>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemSsgsagBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_ssgsag, parent, false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.poster = items[position]
        holder.dataBinding.rvPosterItemLlRightContent.visibility = View.GONE
        holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.VISIBLE

//        holder.dataBinding.rvPosterItemRvContent.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    return@setOnTouchListener true
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    return@setOnTouchListener false
//                }
//                MotionEvent.ACTION_UP -> {
//                    if(holder.dataBinding.rvPosterItemRlLeftContent.visibility == View.VISIBLE) {
//                        holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.GONE
//                        holder.dataBinding.rvPosterItemLlRightContent.visibility = View.VISIBLE
//                    } else {
//                        holder.dataBinding.rvPosterItemLlRightContent.visibility = View.GONE
//                        holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.VISIBLE
//                    }
//                    return@setOnTouchListener true
//                }
//                else -> {
//                    return@setOnTouchListener false
//                }
//            }
//        }
        val gestureDetector = GestureDetector(ctx, SingleTapConfirm())
        holder.dataBinding.rvPosterItemRvContent.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (gestureDetector.onTouchEvent(event)) {
                    holder.itemView.performClick()
                }
                Log.d("fasd", event.toString())
                return false
            }
        })

        holder.itemView.setOnClickListener {
            if(holder.dataBinding.rvPosterItemRlLeftContent.visibility == View.VISIBLE) {
                holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.GONE
                holder.dataBinding.rvPosterItemLlRightContent.visibility = View.VISIBLE
            } else {
                holder.dataBinding.rvPosterItemLlRightContent.visibility = View.GONE
                holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.VISIBLE
            }
        }

//        holder.itemView.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    return@setOnTouchListener true
//                }
//                MotionEvent.ACTION_UP -> {//클릭
//                    if(holder.dataBinding.rvPosterItemRlLeftContent.visibility == View.VISIBLE) {
//                        holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.GONE
//                        holder.dataBinding.rvPosterItemLlRightContent.visibility = View.VISIBLE
//                    } else {
//                        holder.dataBinding.rvPosterItemLlRightContent.visibility = View.GONE
//                        holder.dataBinding.rvPosterItemRlLeftContent.visibility = View.VISIBLE
//                    }
//                    return@setOnTouchListener true
//                }
//                else -> {
//                    return@setOnTouchListener false
//                }
//            }
//        }
    }

    inner class ViewHolder(val dataBinding: ItemSsgsagBinding) : RecyclerView.ViewHolder(dataBinding.root)

    interface OnItemClickListener {
        fun onItemClicked(item: Any?)
    }
}

private class SingleTapConfirm : GestureDetector.SimpleOnGestureListener() {
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }
}