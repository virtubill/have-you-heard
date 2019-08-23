package com.billwetter.haveyouheard.ui.trending

import com.billwetter.haveyouheard.BR
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.ui.common.BindingViewItem
import com.billwetter.haveyourheard.data.model.Article

class ArticleViewItem(val article: Article,
                      override val stableId: Long,
                      override val bindResource: Int = BR.articleViewItem,
                      override val layoutId: Int = R.layout.article_view_item) : BindingViewItem {

    val title: String = article.title
    val imgSrc: String? = article.urlToImage
    val content: String? = article.content
    val source: String = article.source.name
    val publishDate: String = article.publishedAt
    val author: String = article.author ?: ""
    val sourceUrl: String = article.url
}