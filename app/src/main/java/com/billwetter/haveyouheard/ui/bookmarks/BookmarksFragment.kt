package com.billwetter.haveyouheard.ui.bookmarks

import android.os.Bundle
import com.billwetter.haveyouheard.BR
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.databinding.BookmarksFragmentBinding
import com.billwetter.haveyouheard.ui.article.ArticleActivity
import com.billwetter.haveyouheard.ui.common.ArticleViewItem
import com.billwetter.haveyouheard.ui.common.BaseFragment
import com.billwetter.haveyouheard.ui.common.CommonPageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bookmarks_fragment.*


class BookmarksFragment : BaseFragment<BookmarksViewModel, BookmarksFragmentBinding>(BookmarksViewModel::class.java, R.layout.bookmarks_fragment) {
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
        title = getString(R.string.title_bookmarks)
        binding.setVariable(BR.viewModel, viewModel)
        bookmarkedArticles.adapter = adapter
        disposables.add(viewModel.bookmarkedArticles
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.submitList(it) })
    }
}
