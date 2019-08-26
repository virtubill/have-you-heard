package com.billwetter.haveyourheard.data.internal

import io.reactivex.Scheduler

internal interface SchedulerProvider {
    fun subscribeOn(): Scheduler
    fun observeOn(): Scheduler
}