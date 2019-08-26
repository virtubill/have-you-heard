package com.billwetter.haveyourheard.data.internal.settings

import com.billwetter.haveyourheard.data.internal.storage.SharedPrefs
import com.billwetter.haveyourheard.data.model.UserSettings
import io.reactivex.Flowable
import javax.inject.Inject

internal class UserSettingsRepository @Inject constructor(private val prefs: SharedPrefs) : SettingRepository {

    override fun save(settings: UserSettings) {
        prefs.country = settings.country
        prefs.language = settings.language
        //TODO: verify it was saved
    }

    override fun get(): Flowable<UserSettings> {
        val settings = UserSettings(prefs.language, prefs.country)

        return Flowable.just(settings)
    }
}