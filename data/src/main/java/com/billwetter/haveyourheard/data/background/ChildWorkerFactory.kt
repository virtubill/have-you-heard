package com.billwetter.haveyourheard.data.background

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
  fun create(appContext: Context, params: WorkerParameters): RxWorker
}