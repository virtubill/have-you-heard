package com.billwetter.haveyouheard.ui.bookmarks

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyouheard.ui.common.viewmodel.BaseViewModel
import com.billwetter.haveyourheard.data.usecase.GetBookmarks
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(getBookmarks: GetBookmarks) : BaseViewModel() {

    val bookmarkedArticles: Flowable<PagedList<BindingViewItem>>

    init {
        //TODO: move datasource code to Dagger
        val factory = BookmarksDataSourceFactory(getBookmarks, disposables)
        val config = PagedList.Config.Builder()
            .setPageSize(100)
            .setInitialLoadSizeHint(200)
            .setEnablePlaceholders(false)
            .build()

        bookmarkedArticles = RxPagedListBuilder(factory, config).buildFlowable(BackpressureStrategy.LATEST)
    }
}