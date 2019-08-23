package com.billwetter.haveyouheard.ui.article

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.billwetter.haveyouheard.ui.common.viewmodel.BaseViewModel
import com.billwetter.haveyouheard.ui.trending.ArticleViewItem
import com.billwetter.haveyourheard.data.model.Article
import javax.inject.Inject

class ArticleViewModel @Inject constructor() : BaseViewModel() {
    val articleViewItem = ObservableField<ArticleViewItem>()
    val showSource = MutableLiveData<Uri>()

    fun load(article: Article) {
        articleViewItem.set(ArticleViewItem(article, 0))
        articleViewItem.notifyChange()
    }

    fun openArticleSource() {
        //TODO: send message about source not loading if it doesnt exist
        articleViewItem.get()?.sourceUrl?.let {
            showSource.value = Uri.parse(it)
        }
    }
}