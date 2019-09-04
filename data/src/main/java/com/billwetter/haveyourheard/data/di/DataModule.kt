package com.billwetter.haveyourheard.data.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.WorkManager
import com.billwetter.haveyourheard.data.BuildConfig
import com.billwetter.haveyourheard.data.CommonSchedulerProvider
import com.billwetter.haveyourheard.data.SchedulerProvider
import com.billwetter.haveyourheard.data.background.HaveYouSeenWorkerFactory
import com.billwetter.haveyourheard.data.internal.api.HeaderAuthInterceptor
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.internal.bookmarks.BookmarksRepository
import com.billwetter.haveyourheard.data.internal.storage.AppDatabase
import com.billwetter.haveyourheard.data.internal.trending.TrendingApiRepository
import com.billwetter.haveyourheard.data.internal.trending.TrendingRepository
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

    @Singleton
    @Provides
    fun providesWorkManager(context: Context, workFactory: HaveYouSeenWorkerFactory): WorkManager {
        WorkManager.initialize(
            context,
            Configuration.Builder()
                .setWorkerFactory(workFactory)
                .build()
        )

        return WorkManager.getInstance(context)
    }

    @Provides
    internal fun providesTrendingRepository(appDatabase: AppDatabase, newsService: NewsService, schedulerProvider: SchedulerProvider): TrendingRepository {
        return TrendingApiRepository(appDatabase.articleDao(), newsService, schedulerProvider)
    }

    @Provides
    internal fun providesBookmarksRepository(appDatabase: AppDatabase, schedulerProvider: SchedulerProvider): BookmarksRepository {
        return BookmarksRepository(appDatabase.articleDao(), schedulerProvider)
    }

    @Provides
    internal fun providesSchedulerProvider(): SchedulerProvider {
        return CommonSchedulerProvider()
    }

    @Singleton
    @Provides
    internal fun providesRoomDB(applicationContext: Application): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "articles-cache")
            .fallbackToDestructiveMigration() //TODO: add a migration plan
            .build()
    }

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