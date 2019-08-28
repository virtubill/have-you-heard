package com.billwetter.haveyourheard.data.usecase

import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.UseCaseInput
import com.billwetter.haveyourheard.data.internal.bookmarks.BookmarksRepository
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.toResult
import io.reactivex.Flowable
import javax.inject.Inject

class AddBookmark @Inject internal constructor(private val bookmarksRepository: BookmarksRepository) : UseCaseInput<Long,Article> {
    override fun execute(input: Article): Flowable<Result<Long>> {
       return bookmarksRepository.add(input).toFlowable().toResult()
    }

}