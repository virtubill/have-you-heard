package com.billwetter.haveyourheard.data

import io.reactivex.Flowable
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import kotlin.math.pow

internal fun <T> Flowable<T>.onErrorRetry(retries: Int): Flowable<T> {
    return retryWhen(RetryWithBackoffDelay(retries))
}

/**
 * This maps a stream value to a Result<T> model.  This is to help with error handling.
 *
 * You should not call this twice within a stream, it will throw an IllegalException error and
 * continue the stream. This can cause an error to get swallowed if you are using something like a switchMap
 */
internal fun <T> Flowable<T>.toResult(): Flowable<Result<T>> {
    return map { handleSuccess(it) }.onErrorReturn { handleError(it) }
}

private fun <T> handleSuccess(it: T): Result<T> {
    return if (it is Result<*>) {
        throw IllegalArgumentException("This has already been mapped to Result<T>")
    } else {
        Result.Success(it)
    }
}

private fun <T> handleError(throwable: Throwable): Result<T> {
    return when (throwable) {
        is HttpException -> handleHttpException(throwable)
        is UnknownHostException -> Result.Failure("There is a problem with your network connection, please try again later", throwable)
        is IOException -> Result.Failure("There was an error processing this request, please try again later.", throwable)
        else -> throw throwable
    }
}

private fun <T> handleHttpException(ex: HttpException): Result<T> {
    return when (ex.code()) {
        403 -> Result.AuthError("Unauthorized")
        else -> Result.Failure("Server has encountered an error processing your request, please try again later.", ex)
    }
}

private class RetryWithBackoffDelay(private val maxRetries: Int) : Function<Flowable<Throwable>, Flowable<Long>> {
    override fun apply(attempts: Flowable<Throwable>): Flowable<Long> {
        return attempts
                .flatMap(object : Function<Throwable, Flowable<Long>> {
                    private var retryCount: Int = 0
                    override fun apply(throwable: Throwable): Flowable<Long> {
                        return if (++retryCount < maxRetries) {
                            Flowable.timer(1.0.pow(retryCount.toDouble()).toLong(), TimeUnit.SECONDS)
                        } else Flowable.error(throwable)
                    }
                })
    }
}