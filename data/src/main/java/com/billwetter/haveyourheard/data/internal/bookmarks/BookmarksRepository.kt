package com.billwetter.haveyourheard.data.internal.bookmarks

import com.billwetter.haveyourheard.data.SchedulerProvider
import com.billwetter.haveyourheard.data.internal.storage.ArticleDao
import com.billwetter.haveyourheard.data.model.Article
import io.reactivex.Single
import javax.inject.Inject

internal class BookmarksRepository @Inject constructor(private val dao: ArticleDao, private val schedulerProvider: SchedulerProvider) {
    fun getAll(): Single<List<Article>> {
        return dao.getBookmarked()
            .subscribeOn(schedulerProvider.subscribeOn())
            .observeOn(schedulerProvider.observeOn())
    }

    fun get(localId: Long): Single<Article> {
        return dao.loadById(localId)
            .subscribeOn(schedulerProvider.subscribeOn())
            .observeOn(schedulerProvider.observeOn())
    }

    fun add(article: Article): Single<Long> {
        return dao.insert(article)
            .subscribeOn(schedulerProvider.subscribeOn())
            .observeOn(schedulerProvider.observeOn())
    }

    fun remove(article: Article): Single<Int> {
        return dao.delete(article)
            .subscribeOn(schedulerProvider.subscribeOn())
            .observeOn(schedulerProvider.observeOn())
    }
}