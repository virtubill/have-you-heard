package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.internal.SchedulerProvider
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.onErrorRetry
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingApiRepository @Inject constructor(private val newsService: NewsService,
                                                private val schedulerProvider: SchedulerProvider) : TrendingRepository {
    override fun get(country: String): Flowable<List<Article>> {
       return newsService.getTrending(country)
           .subscribeOn(schedulerProvider.subscribeOn())
           .observeOn(schedulerProvider.observeOn())
           .map { it.articles }
           .onErrorRetry(3)
    }
}