package com.billwetter.haveyouheard.di

import com.billwetter.haveyouheard.HaveYouHeardApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [com.billwetter.haveyourheard.data.DataModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: HaveYouHeardApp)
}

