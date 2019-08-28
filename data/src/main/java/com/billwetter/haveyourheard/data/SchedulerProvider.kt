package com.billwetter.haveyourheard.data

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun subscribeOn(): Scheduler
    fun observeOn(): Scheduler
}