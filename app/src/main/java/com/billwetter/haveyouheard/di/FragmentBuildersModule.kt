package com.billwetter.haveyouheard.di

import com.billwetter.haveyouheard.ui.trending.TrendingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindTrendingFragment(): TrendingFragment
}