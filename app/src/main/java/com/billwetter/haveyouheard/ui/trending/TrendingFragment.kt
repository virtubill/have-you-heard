package com.billwetter.haveyouheard.ui.trending

import android.os.Bundle
import com.billwetter.haveyouheard.BR
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.databinding.TrendingFragmentBinding
import com.billwetter.haveyouheard.ui.common.BaseFragment
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyouheard.ui.common.CommonBindingAdapter
import kotlinx.android.synthetic.main.trending_fragment.*


class TrendingFragment : BaseFragment<TrendingViewModel, TrendingFragmentBinding>(TrendingViewModel::class.java, R.layout.trending_fragment) {
    private lateinit var adapter: CommonBindingAdapter<BindingViewItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CommonBindingAdapter(viewModel.trendingArticles) {
            (it as? ArticleViewItem)?.let {

            }
        }
    }

    override fun prepareView() {
        title = getString(R.string.trending_news)
        binding.setVariable(BR.viewModel, viewModel)
        trendingNews.adapter = adapter
    }
}
