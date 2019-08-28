package com.billwetter.haveyouheard.ui.bookmarks

import androidx.paging.DataSource
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyourheard.data.usecase.GetBookmarks
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.disposables.CompositeDisposable

class BookmarksDataSourceFactory(private val getBookmarks: GetBookmarks, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, BindingViewItem>() {
    override fun create(): DataSource<Int, BindingViewItem> {
        return BookmarksDataSource(getBookmarks, compositeDisposable)
    }
}