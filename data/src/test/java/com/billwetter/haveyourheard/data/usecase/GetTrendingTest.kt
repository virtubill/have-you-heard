package com.billwetter.haveyourheard.data.usecase

import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.createArticlesResponseHttpException
import com.billwetter.haveyourheard.data.internal.trending.TrendingRepository
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.TrendingParams
import io.reactivex.Flowable
import org.junit.Test
import java.util.concurrent.TimeUnit

class GetTrendingTest {
    @Test
    fun testSuccess() {
        val trendingApiRepoMock = object : TrendingRepository {
            override fun get(params: TrendingParams): Flowable<List<Article>> {
                return Flowable.just(listOf(Article()))
            }
        }

        val getTrending = GetTrending(trendingApiRepoMock)
        val testSubscriber = getTrending.execute(1).test()

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue { it is Result.Success }
    }

    @Test
    fun testFormattingError() {
        val trendingApiRepoMock = object : TrendingRepository {
            override fun get(params: TrendingParams): Flowable<List<Article>> {
                return Flowable.error(createArticlesResponseHttpException(400))
            }

        }

        val getTrending = GetTrending(trendingApiRepoMock)
        val testSubscriber = getTrending.execute(1).test()
        testSubscriber.awaitTerminalEvent(4, TimeUnit.SECONDS) // wait for retires to finish

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue {
            it is Result.Failure
        }
    }

    @Test
    fun testAuthError() {
        val trendingApiRepoMock = object : TrendingRepository {
            override fun get(params: TrendingParams): Flowable<List<Article>> {
                return Flowable.error(createArticlesResponseHttpException(403))
            }

        }

        val getTrending = GetTrending(trendingApiRepoMock)
        val testSubscriber = getTrending.execute(1).test()
        testSubscriber.awaitTerminalEvent(4, TimeUnit.SECONDS) // wait for retires to finish

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue {
            it is Result.AuthError
        }
    }
}