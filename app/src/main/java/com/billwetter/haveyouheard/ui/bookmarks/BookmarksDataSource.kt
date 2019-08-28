package com.billwetter.haveyouheard.ui.bookmarks

import androidx.paging.PageKeyedDataSource
import com.billwetter.haveyouheard.ui.common.ArticleViewItem
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.usecase.GetBookmarks
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BookmarksDataSource @Inject constructor(private val getBookmarks: GetBookmarks,
                                              private val disposables: CompositeDisposable
                                             ) : PageKeyedDataSource<Int, BindingViewItem>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BindingViewItem>) {
        disposables.add(getBookmarks.execute().subscribe {
            when(it) {
                is Result.Success -> callback.onResult(
                    it.value.map { article ->
                        ArticleViewItem(
                            article,
                            article.url.hashCode().toLong()
                        )
                    },
                    null,
                    null)
                else -> { /** TODO: handle errors for display */ }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BindingViewItem>) {
        //TODO: setup paging
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BindingViewItem>) {
        //no-op
    }
}