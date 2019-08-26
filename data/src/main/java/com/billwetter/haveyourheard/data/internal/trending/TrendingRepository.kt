package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.model.TrendingParams
import io.reactivex.Flowable

internal interface TrendingRepository {
    fun get(params: TrendingParams) : Flowable<List<Article>>
}