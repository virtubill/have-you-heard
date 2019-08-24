package com.billwetter.haveyourheard.data.internal.trending

import com.billwetter.haveyourheard.data.model.Article
import io.reactivex.Flowable

interface TrendingRepository {
    fun get(country: String) : Flowable<List<Article>>
}