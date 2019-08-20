package com.billwetter.haveyourheard.data

import io.reactivex.Flowable

interface UseCase<T> {
    fun execute(): Flowable<Result<T>>
}

interface UseCaseInput<T, in P> {
    fun execute(input: P): Flowable<Result<T>>
}