package com.billwetter.haveyouheard.ui.trending

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyouheard.ui.common.viewmodel.BaseViewModel
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class TrendingViewModel @Inject constructor(trending: GetTrending) : BaseViewModel() {

    val trendingArticles: Flowable<PagedList<BindingViewItem>>

    init {
        //TODO: move datasource code to Dagger
        val factory = TrendingDataSourceFactory(trending, disposables)
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(40)
            .setEnablePlaceholders(false)
            .build()

        trendingArticles = RxPagedListBuilder(factory, config).buildFlowable(BackpressureStrategy.LATEST)
    }
}