package com.billwetter.haveyouheard.ui.trending

import android.os.Bundle
import com.billwetter.haveyouheard.BR
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.databinding.TrendingFragmentBinding
import com.billwetter.haveyouheard.ui.article.ArticleActivity
import com.billwetter.haveyouheard.ui.common.BaseFragment
import com.billwetter.haveyouheard.ui.common.CommonPageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.trending_fragment.*


class TrendingFragment : BaseFragment<TrendingViewModel, TrendingFragmentBinding>(TrendingViewModel::class.java, R.layout.trending_fragment) {
    private lateinit var adapter: CommonPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CommonPageAdapter {
            (it as? ArticleViewItem)?.let { articleViewItem ->
                ArticleActivity.create(context!!, articleViewItem.article)
            }
        }
    }

    override fun prepareView() {
        title = getString(R.string.trending_news)
        binding.setVariable(BR.viewModel, viewModel)
        trendingNews.adapter = adapter
        disposables.add(viewModel.trendingArticles.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { adapter.submitList(it) })
    }
}
