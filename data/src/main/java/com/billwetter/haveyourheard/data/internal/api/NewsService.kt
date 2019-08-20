package com.billwetter.haveyourheard.data.internal.api

import com.billwetter.haveyourheard.data.model.ArticlesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getTrending(@Query("country") country: String) : Flowable<ArticlesResponse>
}