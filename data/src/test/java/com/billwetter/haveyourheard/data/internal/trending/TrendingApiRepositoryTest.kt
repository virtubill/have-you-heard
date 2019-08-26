package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.createArticlesResponseHttpException
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.mockSchedulers
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.ArticlesResponse
import com.billwetter.haveyourheard.data.model.TrendingParams
import io.reactivex.Flowable
import org.junit.Test
import retrofit2.HttpException
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class TrendingApiRepositoryTest {
    @Test
    fun getSuccess() {
        val newsService = object : NewsService {
            override fun getTrending(params: TrendingParams): Flowable<ArticlesResponse> {
                val response = ArticlesResponse(articles = listOf(Article()), status = "OK", totalResults = 1)
                return Flowable.just(response)
            }

        }
        val repo = TrendingApiRepository(newsService, mockSchedulers())

        val testSubscriber = repo.get(TrendingParams(country = "us")).test()

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue { it.size == 1 }
    }

    @Test
    fun getReponseFromBadRequest() {
        val newsService = object : NewsService {
            override fun getTrending(params: TrendingParams): Flowable<ArticlesResponse> {
                return Flowable.error(createArticlesResponseHttpException(400))
            }

        }
        val repo = TrendingApiRepository(newsService, mockSchedulers())

        val testSubscriber = repo.get(TrendingParams(country = "us")).test()
        testSubscriber.awaitTerminalEvent(4, TimeUnit.SECONDS) // wait for retires to finish

        testSubscriber.assertError { it is HttpException && it.code() == 400 }
        testSubscriber.assertTerminated()
    }


    @Test
    fun getReponseFromMissingCountry() {
        val newsService = object : NewsService {
            override fun getTrending(params: TrendingParams): Flowable<ArticlesResponse> {
                return Flowable.error(createArticlesResponseHttpException(400))
            }

        }
        val repo = TrendingApiRepository(newsService, mockSchedulers())

        val testSubscriber = repo.get(TrendingParams()).test()
        testSubscriber.awaitTerminalEvent(4, TimeUnit.SECONDS) // wait for retires to finish

        testSubscriber.assertError { it is IllegalArgumentException }
        testSubscriber.assertTerminated()
    }
}