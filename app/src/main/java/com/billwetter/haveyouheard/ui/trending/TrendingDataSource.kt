package com.billwetter.haveyouheard.ui.trending

import androidx.paging.PageKeyedDataSource
import com.billwetter.haveyouheard.ui.common.ArticleViewItem
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TrendingDataSource @Inject constructor(private val getTrending: GetTrending,
                                             private val disposables: CompositeDisposable
                                             ) : PageKeyedDataSource<Int, BindingViewItem>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BindingViewItem>) {
        disposables.add(getTrending.execute(1).subscribe {
            when(it) {
                is Result.Success -> callback.onResult(
                    it.value.map { article ->
                        ArticleViewItem(
                            article,
                            article.localId
                        )
                    },
                    null,
                    2)
                else -> { /** TODO: handle errors for display */ }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BindingViewItem>) {
        disposables.add(getTrending.execute(params.key).subscribe {
            when(it) {
                is Result.Success -> callback.onResult(
                    it.value.map { article ->
                        ArticleViewItem(
                            article,
                            article.localId
                        )
                    },
                    params.key + 1)
                else -> { /** TODO: handle errors for display */ }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BindingViewItem>) {
        //no-op
    }
}