package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.createArticlesResponseHttpException
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.mockSchedulers
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.ArticlesResponse
import io.reactivex.Flowable
import org.junit.Test
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class TrendingApiRepositoryTest {
    @Test
    fun getSuccess() {
        val newsService = object : NewsService {
            override fun getTrending(country: String): Flowable<ArticlesResponse> {
                val response = ArticlesResponse(articles = listOf(Article()), status = "OK", totalResults = 1)
                return Flowable.just(response)
            }

        }
        val repo = TrendingApiRepository(newsService, mockSchedulers())

        val testSubscriber = repo.get("us").test()

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue { it.size == 1 }
    }

    @Test
    fun getReponseFromBadRequest() {
        val newsService = object : NewsService {
            override fun getTrending(country: String): Flowable<ArticlesResponse> {
                return Flowable.error(createArticlesResponseHttpException(400))
            }

        }
        val repo = TrendingApiRepository(newsService, mockSchedulers())

        val testSubscriber = repo.get("us").test()
        testSubscriber.awaitTerminalEvent(4, TimeUnit.SECONDS) // wait for retires to finish

        testSubscriber.assertError { it is HttpException && it.code() == 400 }
        testSubscriber.assertTerminated()
    }
}