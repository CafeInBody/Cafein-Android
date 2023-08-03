package sso.hyeon.wachacha

import android.content.SharedPreferences
import io.reactivex.subjects.PublishSubject

class LiveSharedPreferences constructor(private val preferences: SharedPreferences) {
    private val publisher = PublishSubject.create<String>()
    private val listener = SharedPreferences
        .OnSharedPreferenceChangeListener { _, key -> publisher.onNext(key) }

    private val updates = publisher.doOnSubscribe {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }.doOnDispose {
        if (!publisher.hasObservers()) {
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun getString(key: String, defaultValue: String): LivePreference<String> {
        return LivePreference(updates, preferences, key, defaultValue)
    }
}
