package com.billwetter.haveyourheard.data.usecase

import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.UseCaseInput
import com.billwetter.haveyourheard.data.internal.trending.TrendingRepository
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.TrendingParams
import com.billwetter.haveyourheard.data.toResult
import io.reactivex.Flowable
import javax.inject.Inject

class GetTrending @Inject internal constructor(private val trendingApiRepository: TrendingRepository) : UseCaseInput<List<Article>, Int> {
    override fun execute(input: Int): Flowable<Result<List<Article>>> {
        return trendingApiRepository.get(TrendingParams(country = "us", page = input)).toResult()
    }
}