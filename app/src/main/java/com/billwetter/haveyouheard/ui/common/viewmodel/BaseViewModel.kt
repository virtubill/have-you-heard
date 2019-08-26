package com.billwetter.haveyouheard.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(protected val disposables: CompositeDisposable = CompositeDisposable()) : ViewModel() {
    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}