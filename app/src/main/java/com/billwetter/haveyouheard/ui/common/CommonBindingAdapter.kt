package com.billwetter.haveyouheard.ui.common

import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView Adapter to handle views that use Databinding
 */
class CommonBindingAdapter<T : BindingViewItem>(private val items: ObservableList<T>,
                                                      private val clickHandler: (T) -> Unit) : RecyclerView.Adapter<CommonBindingAdapter.CommonViewHolder>() {
    private var recyclerView: RecyclerView? = null

    private val callback = object : ObservableList.OnListChangedCallback<ObservableList<T>>() {

        override fun onChanged(sender: ObservableList<T>) {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, layoutResId: Int): CommonViewHolder = CommonViewHolder(parent.inflateBinding(layoutResId))

    override fun getItemViewType(position: Int): Int = items[position].layoutId

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        with(items[position]) {
            holder.binding.root.setOnClickListener { clickHandler(this) }
            executeBindings(holder.binding)
        }
    }

    override fun getItemCount(): Int = items.size

    class CommonViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return items[position].stableId
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        items.addOnListChangedCallback(callback)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        items.removeOnListChangedCallback(callback)
        this.recyclerView = null
    }

    override fun onViewRecycled(holder: CommonViewHolder) {
        clearImage(holder)
    }
}