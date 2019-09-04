package com.billwetter.haveyouheard

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.billwetter.haveyouheard.di.AppModule
import com.billwetter.haveyouheard.di.DaggerAppComponent
import com.billwetter.haveyourheard.data.background.TrendingArticleWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HaveYouHeardApp : Application(), HasActivityInjector {

    @Inject
    lateinit var workManager: WorkManager
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)

        //TODO: define some constraints for this (maybe based on battery, plugged in, etc)
        val periodicWorkRequest = PeriodicWorkRequestBuilder<TrendingArticleWorker>(15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
            .addTag("BackgroundUpdate")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "BackgroundUpdate",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest)

    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}