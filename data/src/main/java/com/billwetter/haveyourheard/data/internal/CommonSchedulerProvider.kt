package com.billwetter.haveyourheard.data.internal

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class CommonSchedulerProvider : SchedulerProvider {
    override fun subscribeOn(): Scheduler {
        return Schedulers.io()
    }

    override fun observeOn(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}