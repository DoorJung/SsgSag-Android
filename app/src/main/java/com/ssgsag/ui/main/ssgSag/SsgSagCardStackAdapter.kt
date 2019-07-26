package com.ssgsag.ui.main.ssgSag

import android.content.Context
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssgsag.R
import com.ssgsag.data.model.poster.posterDetail.PosterDetail
import com.ssgsag.databinding.ItemSsgsagBinding
import com.ssgsag.util.view.NonScrollLinearLayoutManager

class SsgSagCardStackAdapter(val ctx: Context) :

    RecyclerView.Adapter<SsgSagCardStackAdapter.ViewHolder>() {

    private var itemTouch: Boolean = false

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
        holder.dataBinding.itemSsgsagLlRightContent.visibility = View.GONE
        holder.dataBinding.itemSsgsagRlLeftContent.visibility = View.VISIBLE

        holder.dataBinding.rvPosterItemRvContent.layoutManager = NonScrollLinearLayoutManager(ctx)

        val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                itemTouch = false
            }
        }

        val onItemTouchListener = object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(parent: RecyclerView, evt: MotionEvent): Boolean {
                if (MotionEvent.ACTION_UP == evt.action && itemTouch) {
                    holder.dataBinding.itemSsgsagRlLeftContent.visibility = View.VISIBLE
                    holder.dataBinding.itemSsgsagLlRightContent.visibility = View.GONE
                } else if (MotionEvent.ACTION_DOWN == evt.action) {
                    itemTouch = true
                }
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        }
        holder.dataBinding.rvPosterItemRvContent.addOnItemTouchListener(onItemTouchListener)
        holder.dataBinding.rvPosterItemRvContent.addOnScrollListener(onScrollChangeListener)


        holder.itemView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    if (holder.dataBinding.itemSsgsagLlRightContent.visibility == View.VISIBLE) {
                        holder.dataBinding.itemSsgsagRlLeftContent.visibility = View.VISIBLE
                        holder.dataBinding.itemSsgsagLlRightContent.visibility = View.GONE
                    } else {
                        holder.dataBinding.itemSsgsagRlLeftContent.visibility = View.GONE
                        holder.dataBinding.itemSsgsagLlRightContent.visibility = View.VISIBLE
                    }
                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
        }
    }

    inner class ViewHolder(val dataBinding: ItemSsgsagBinding) : RecyclerView.ViewHolder(dataBinding.root)
}