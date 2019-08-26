package com.billwetter.haveyouheard.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CommonPageAdapter(private val clickHandler: (BindingViewItem) -> Unit) : PagedListAdapter<BindingViewItem, CommonPageAdapter.CommonViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = getItem(position)?.layoutId ?: -1

    override fun onCreateViewHolder(parent: ViewGroup, layoutResId: Int): CommonViewHolder = CommonViewHolder(parent.inflateBinding(layoutResId))

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.binding.root.setOnClickListener { clickHandler(this) }
            executeBindings(holder.binding)
        }
    }

    override fun onViewRecycled(holder: CommonViewHolder) {
        clearImage(holder)
    }

    class CommonViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<BindingViewItem>() {
            override fun areItemsTheSame(oldItem: BindingViewItem,
                                         newItem: BindingViewItem) = oldItem.stableId == newItem.stableId

            override fun areContentsTheSame(oldItem: BindingViewItem,
                                            newItem: BindingViewItem) = oldItem.stableId == newItem.stableId
        }
    }
}