package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.onErrorRetry
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepository @Inject constructor(private val newsService: NewsService) {
    fun get(country: String): Flowable<List<Article>> {
       return newsService.getTrending(country)
           .subscribeOn(Schedulers.io())
           .map { it.articles }
           .onErrorRetry(3)
    }
}