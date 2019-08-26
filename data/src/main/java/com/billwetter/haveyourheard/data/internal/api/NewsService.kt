package com.billwetter.haveyourheard.data.internal.api

import com.billwetter.haveyourheard.data.model.ArticlesResponse
import com.billwetter.haveyourheard.data.model.TrendingParams
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface NewsService {
    @GET("top-headlines")
    fun getTrending(@QueryMap params: TrendingParams) : Flowable<ArticlesResponse>
}