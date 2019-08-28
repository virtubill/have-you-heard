package com.billwetter.haveyourheard.data

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommonSchedulerProvider : SchedulerProvider {
    override fun subscribeOn(): Scheduler {
        return Schedulers.io()
    }

    override fun observeOn(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}