package com.billwetter.haveyouheard.ui.trending

import androidx.databinding.ObservableArrayList
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyouheard.ui.common.viewmodel.BaseViewModel
import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.usecase.GetTrending
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val trending: GetTrending) : BaseViewModel() {
    val trendingArticles = ObservableArrayList<BindingViewItem>()
    private var currentPage = 0

    init {
        currentPage = 0
        loadNext()
    }

    fun loadNext() {
        //TOD): add pagination
        disposables.add(trending.execute().observeOn(AndroidSchedulers.mainThread()).subscribe {
            when(it) {
                is Result.Success -> addArticles(it.value)
            }
        })
    }

    private fun addArticles(articles: List<Article>) {
        var index = trendingArticles.size.toLong()
        trendingArticles.addAll(articles.map { ArticleViewItem(it, index++) })
    }
}