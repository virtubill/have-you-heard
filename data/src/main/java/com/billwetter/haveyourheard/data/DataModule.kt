package com.billwetter.haveyourheard.data

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.billwetter.haveyourheard.data.internal.api.HeaderAuthInterceptor
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    // API SERVICES
    @Singleton
    @Provides
    internal fun providesNewsService(@Named("AuthClient") retrofit: Retrofit): NewsService = retrofit.create(NewsService::class.java)

    //NETWORK CLIENTS
    @Singleton
    @Provides
    @Named("AuthClient")
    internal fun providesRetrofit(@Named("AuthClient") client: OkHttpClient): Retrofit {
        val gson = GsonBuilder().create()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Singleton
    @Provides
    @Named("AuthClient")
    internal fun providesOkHttpClient(application: Application): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .cache(Cache(application.cacheDir, 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(HeaderAuthInterceptor(BuildConfig.API_KEY))
                .followRedirects(true)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    internal fun providesSharedPreferences(application: Application) = application.getSharedPreferences("HaveYouHeard", MODE_PRIVATE)
}