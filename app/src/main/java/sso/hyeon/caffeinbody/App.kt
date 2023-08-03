package sso.hyeon.caffeinbody

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var prefs : MySharedPreferences

        fun context() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        instance = this
        super.onCreate()
    }
}