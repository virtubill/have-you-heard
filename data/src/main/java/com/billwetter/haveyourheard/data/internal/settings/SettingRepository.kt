package com.billwetter.haveyourheard.data.internal.settings

import com.billwetter.haveyourheard.data.model.UserSettings
import io.reactivex.Flowable

internal interface SettingRepository {
    fun get(): Flowable<UserSettings>
    fun save(settings: UserSettings)
}