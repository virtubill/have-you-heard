package com.billwetter.haveyourheard.data.di

import androidx.work.RxWorker
import androidx.work.Worker
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class WorkerKey(val value: KClass<out RxWorker>)