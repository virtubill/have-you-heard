package com.billwetter.haveyouheard.ui.article

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.databinding.ArticleActivityBinding
import com.billwetter.haveyouheard.ui.common.BaseActivity
import com.billwetter.haveyourheard.data.model.Article


class ArticleActivity : BaseActivity<ArticleViewModel, ArticleActivityBinding>(ArticleViewModel::class.java, R.layout.article_activity) {
    override fun prepareView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        intent?.extras?.getParcelable<Article>("article")?.let {
            viewModel.load(it)
        }

        viewModel.showSource.observe(this, Observer {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, it)
            startActivity(openBrowserIntent)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun create(context: Context, article: Article) {
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra("article", article)
            context.startActivity(intent)
        }
    }
}