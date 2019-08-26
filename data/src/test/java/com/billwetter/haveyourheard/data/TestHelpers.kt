package com.billwetter.haveyourheard.data

import com.billwetter.haveyourheard.data.internal.SchedulerProvider
import com.billwetter.haveyourheard.data.model.ArticlesResponse
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ExecutorScheduler
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.Executor

internal fun mockSchedulers() = object : SchedulerProvider {
    override fun subscribeOn(): Scheduler = object : Scheduler() {
        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
        }
    }

    override fun observeOn(): Scheduler = object : Scheduler() {
        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
        }
    }
}

fun createArticlesResponseHttpException(code: Int): HttpException {
    val theBody = "{}".toResponseBody("application/json".toMediaType())
    val response = Response.error<ArticlesResponse>(code, theBody)

    return HttpException(response)
}