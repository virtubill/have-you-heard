package com.billwetter.haveyouheard.ui.common.viewmodel

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.billwetter.haveyouheard.di.PerActivity
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@PerActivity
class ContentStateBus @Inject constructor() : MutableLiveData<ContentAction>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in ContentAction>) {
        if (hasActiveObservers()) {
            Log.v("CONTENT STATE BUS","Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t: ContentAction? ->
            if (pending.compareAndSet(true, false)) {
                if (t!= null) observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: ContentAction?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}