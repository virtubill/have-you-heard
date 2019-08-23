package com.billwetter.haveyouheard.di

import com.billwetter.haveyouheard.ui.MainActivity
import com.billwetter.haveyouheard.ui.article.ArticleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class ActivityBuildersModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ViewModelModule::class])
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ViewModelModule::class])
    abstract fun bindArticleActivity(): ArticleActivity

}