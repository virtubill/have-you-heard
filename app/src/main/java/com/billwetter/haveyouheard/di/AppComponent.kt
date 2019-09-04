package com.billwetter.haveyouheard.di

import com.billwetter.haveyouheard.HaveYouHeardApp
import com.billwetter.haveyourheard.data.di.DataModule
import com.billwetter.haveyourheard.data.di.WorkerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, WorkerModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: HaveYouHeardApp)
}

