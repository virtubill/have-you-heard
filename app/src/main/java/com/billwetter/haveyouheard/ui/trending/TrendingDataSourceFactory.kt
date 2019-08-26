package com.billwetter.haveyouheard.ui.trending

import androidx.paging.DataSource
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.disposables.CompositeDisposable

class TrendingDataSourceFactory(private val getTrending: GetTrending, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, BindingViewItem>() {
    override fun create(): DataSource<Int, BindingViewItem> {
        return TrendingDataSource(getTrending, compositeDisposable)
    }
}