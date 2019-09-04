package com.billwetter.haveyourheard.data.background

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.billwetter.haveyourheard.data.internal.api.NewsService
import com.billwetter.haveyourheard.data.internal.storage.AppDatabase
import com.billwetter.haveyourheard.data.internal.storage.ArticleDao
import com.billwetter.haveyourheard.data.internal.trending.TrendingRepository
import com.billwetter.haveyourheard.data.model.TrendingParams
import com.billwetter.haveyourheard.data.onErrorRetry
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrendingArticleWorker internal constructor(private val trendingRepo: TrendingRepository,
                                                 private val dao: ArticleDao,
                                                 context: Context,
                                                 workerParameters: WorkerParameters) : RxWorker(context, workerParameters){
    override fun createWork(): Single<Result> {
        return trendingRepo.get(TrendingParams(country = "us", page = 1, pageSize = 40))
            .subscribeOn(Schedulers.io())
            .switchMap{
                dao.insertAll(it).toFlowable().subscribeOn(Schedulers.io())
            }
            .singleOrError()
            .map { Result.success() }
            .onErrorReturn { Result.failure() }
    }

    class Factory @Inject internal constructor(
        private val trendingRepo: TrendingRepository,
        private val appDatabase: AppDatabase
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): RxWorker {
            return TrendingArticleWorker(
                trendingRepo,
                appDatabase.articleDao(),
                appContext,
                params
            )
        }
    }
}