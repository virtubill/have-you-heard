package com.billwetter.haveyouheard.ui.article

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.databinding.ArticleActivityBinding
import com.billwetter.haveyouheard.ui.common.BaseActivity
import com.billwetter.haveyouheard.ui.common.findDrawable
import com.billwetter.haveyouheard.ui.common.openUri
import com.billwetter.haveyourheard.data.model.Article

class ArticleActivity : BaseActivity<ArticleViewModel, ArticleActivityBinding>(ArticleViewModel::class.java, R.layout.article_activity) {
    override fun prepareView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        intent?.extras?.getParcelable<Article>("article")?.let {
            viewModel.load(it)
        }

        viewModel.showSource.observe(this, Observer {
            openUri(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.bookmark_article -> {
                viewModel.toggleBookmark()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bookmark_menu, menu)
        // lneed to start watching this after menu is inflated
        viewModel.hasBookmark.observe(this, Observer { isBookmarked ->
            menu?.findItem(R.id.bookmark_article)?.apply {
                icon = if (isBookmarked) {
                    findDrawable(R.drawable.ic_bookmark)
                } else {
                    findDrawable(R.drawable.ic_bookmark_border)
                }

            }
        })
        return true
    }

    companion object {
        fun create(context: Context, article: Article) {
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra("article", article)
            context.startActivity(intent)
        }
    }
}