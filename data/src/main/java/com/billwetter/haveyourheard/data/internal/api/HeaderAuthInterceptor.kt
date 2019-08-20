package com.billwetter.haveyourheard.data.internal.api

import okhttp3.Interceptor
import okhttp3.Response

internal class HeaderAuthInterceptor(private val headerKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val headers = request
                .headers
                .newBuilder()
                .add("X-Api-Key", headerKey)
                .build()

        return chain.proceed(request
                .newBuilder()
                .headers(headers)
                .build())
    }
}