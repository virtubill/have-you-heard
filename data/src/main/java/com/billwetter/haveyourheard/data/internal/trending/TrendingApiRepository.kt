package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.SchedulerProvider
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.TrendingParams
import com.billwetter.haveyourheard.data.onErrorRetry
import io.reactivex.Flowable
import java.security.InvalidParameterException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TrendingApiRepository @Inject constructor(private val newsService: NewsService,
                                                private val schedulerProvider: SchedulerProvider
) : TrendingRepository {
    override fun get(params: TrendingParams): Flowable<List<Article>> {
        return if (params.containsKey("country")) {
            newsService.getTrending(params)
                .subscribeOn(schedulerProvider.subscribeOn())
                .observeOn(schedulerProvider.observeOn())
                .map { it.articles }
                .onErrorRetry(3)
        } else {
            Flowable.error<List<Article>>(InvalidParameterException())
                .subscribeOn(schedulerProvider.subscribeOn())
                .observeOn(schedulerProvider.observeOn())
        }
    }
}