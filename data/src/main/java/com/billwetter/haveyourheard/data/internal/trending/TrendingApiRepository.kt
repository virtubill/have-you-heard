package com.billwetter.haveyourheard.data.internal.trending

import android.util.Log
import com.billwetter.haveyourheard.data.SchedulerProvider
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.internal.storage.ArticleDao
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.TrendingParams
import com.billwetter.haveyourheard.data.onErrorRetry
import io.reactivex.Flowable
import java.security.InvalidParameterException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TrendingApiRepository @Inject constructor(private val dao: ArticleDao,
                                                         private val newsService: NewsService,
                                                         private val schedulerProvider: SchedulerProvider) : TrendingRepository {
    override fun get(params: TrendingParams): Flowable<List<Article>> {
        return if (params.containsKey("country") && params.containsKey("page")) {
            newsService.getTrending(params)
                .subscribeOn(schedulerProvider.subscribeOn())
                .observeOn(schedulerProvider.observeOn())
                .map {
                    it.articles.map { article ->
                        article.localId = article.url.hashCode().toLong()
                        article
                    }
                }
//                .doOnNext {
//                   //TODO: handle errors
//                    dao.insertAll(it).toFlowable().subscribeOn(schedulerProvider.subscribeOn()).blockingFirst()
//                }
                .onErrorRetry(3)
                .onErrorResumeNext(cachedResults(params["page"]?.toInt() ?: 1))
        } else {
            Flowable.error<List<Article>>(InvalidParameterException())
                .subscribeOn(schedulerProvider.subscribeOn())
                .observeOn(schedulerProvider.observeOn())
        }
    }

    //TODO: need more graceful way to handle this -- news.org api uses page(starts at 1), while db needs an offset
    private fun cachedResults(page: Int) = dao.get(page - 1)
        .subscribeOn(schedulerProvider.subscribeOn())
        .observeOn(schedulerProvider.observeOn())
        .toFlowable()
}