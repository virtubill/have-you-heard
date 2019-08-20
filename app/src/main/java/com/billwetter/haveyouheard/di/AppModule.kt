package com.billwetter.haveyouheard.di

import android.app.Application
import android.content.Context
import com.billwetter.haveyouheard.HaveYouHeardApp
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class])
class AppModule(private val application: HaveYouHeardApp) {
    @Singleton
    @Provides
    fun providesContext(): Context = application.applicationContext

    @Singleton
    @Provides
    fun providesApplication(): Application = application
}