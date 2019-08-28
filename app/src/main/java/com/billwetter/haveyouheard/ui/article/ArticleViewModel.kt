package com.billwetter.haveyouheard.ui.article

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.billwetter.haveyouheard.ui.common.viewmodel.BaseViewModel
import com.billwetter.haveyouheard.ui.common.ArticleViewItem
import com.billwetter.haveyourheard.data.Result
import com.billwetter.haveyourheard.data.model.Article
import com.billwetter.haveyourheard.data.usecase.AddBookmark
import com.billwetter.haveyourheard.data.usecase.CheckBookmark
import com.billwetter.haveyourheard.data.usecase.RemoveBookmark
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val checkBookmark: CheckBookmark,
                                           private val addBookmark: AddBookmark,
                                           private val removeBookmark: RemoveBookmark) : BaseViewModel() {
    val articleViewItem = ObservableField<ArticleViewItem>()
    //using livedata here, could be replaced by rxRelay or rxSubject?
    val showSource = MutableLiveData<Uri>()
    val hasBookmark = MutableLiveData<Boolean>()

    fun load(article: Article) {
        articleViewItem.set(ArticleViewItem(article, article.localId))
        articleViewItem.notifyChange()

        disposables.add(checkBookmark.execute(article).subscribe {
            hasBookmark.value = when(it) {
                is Result.Success -> it.value
                else -> false
            }
        })
    }

    fun toggleBookmark() {
        articleViewItem.get()?.let {
            if (hasBookmark.value == true) {
                removeBookmark(it.article)
            } else {
                addBookmark(it.article)
            }
        }
    }

    fun openArticleSource() {
        //TODO: send message about source not loading if it doesnt exist
        articleViewItem.get()?.sourceUrl?.let {
            showSource.value = Uri.parse(it)
        }
    }

    private fun addBookmark(article: Article): Boolean {
        return disposables.add(addBookmark.execute(article).subscribe { result ->
            hasBookmark.value = when (result) {
                is Result.Success -> true
                else -> false //TODO: send error message
            }
        })
    }

    private fun removeBookmark(article: Article) {
        disposables.add(removeBookmark.execute(article).subscribe { result ->
            hasBookmark.value = when (result) {
                is Result.Success -> false
                else -> true //TODO: send error message
            }
        })
    }
}