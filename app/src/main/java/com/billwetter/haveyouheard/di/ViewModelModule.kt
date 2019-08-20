package com.billwetter.haveyouheard.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billwetter.haveyouheard.ui.MainViewModel
import com.billwetter.haveyouheard.ui.common.viewmodel.CommonViewModelFactory
import com.billwetter.haveyouheard.ui.trending.TrendingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds @IntoMap
    @ViewModelKey(TrendingViewModel::class)
    abstract fun bindTrendingViewModel(viewModel: TrendingViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CommonViewModelFactory): ViewModelProvider.Factory
}