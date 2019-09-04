package com.billwetter.haveyourheard.data.di

import com.billwetter.haveyourheard.data.background.TrendingArticleWorker
import com.billwetter.haveyourheard.data.background.ChildWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(TrendingArticleWorker::class)
    internal abstract fun bindWorkerFactory(worker: TrendingArticleWorker.Factory): ChildWorkerFactory
}