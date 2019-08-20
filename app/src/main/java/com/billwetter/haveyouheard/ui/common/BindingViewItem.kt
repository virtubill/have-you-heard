package com.billwetter.haveyouheard.ui.common

import androidx.databinding.ViewDataBinding

interface BindingViewItem {
    val stableId: Long
    val bindResource: Int
    val layoutId : Int

    fun executeBindings(binding: ViewDataBinding) {
        binding.setVariable(bindResource, this)
        binding.executePendingBindings()
    }
}
