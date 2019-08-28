package com.billwetter.haveyourheard.data.usecase

import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.UseCase
import com.billwetter.haveyourheard.data.internal.bookmarks.BookmarksRepository
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.toResult
import io.reactivex.Flowable
import javax.inject.Inject

class GetBookmarks @Inject internal constructor(private val bookmarksRepository: BookmarksRepository) : UseCase<List<Article>> {
    override fun execute(): Flowable<Result<List<Article>>> {
       return bookmarksRepository.getAll().toFlowable().toResult()
    }
}