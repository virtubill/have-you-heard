package com.billwetter.haveyourheard.data.usecase

import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.UseCase
import com.billwetter.haveyourheard.data.internal.TrendingRepository
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.toResult
import io.reactivex.Flowable
import javax.inject.Inject

class GetTrending @Inject constructor(private val trendingRepository: TrendingRepository) : UseCase<List<Article>> {
    override fun execute(): Flowable<Result<List<Article>>> {
        return trendingRepository.get("us").toResult()
    }
}