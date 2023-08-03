package sso.hyeon.wachacha

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("caffeinedatas", Context.MODE_PRIVATE)

    var drinkedCaffeine_day: String?
    get() = prefs.getString("drinkedCaffeine_day", null )
    set(value) = prefs.edit().putString("drinkedCaffeine_day", value!!).apply()

    var recommendedCaffeine_day: String?
        get() = prefs.getString("recommendedCaffeine_day", null )
        set(value) = prefs.edit().putString("recommendedCaffeine_day", value!!).apply()

    var drinkedCaffeine_once: String?
        get() = prefs.getString("drinkedCaffeine_once", null )
        set(value) = prefs.edit().putString("drinkedCaffeine_once", value!!).apply()

    var recommendedCaffeine_once: String?
        get() = prefs.getString("recommendedCaffeine_once", null )
        set(value) = prefs.edit().putString("recommendedCaffeine_once", value!!).apply()

    var leftCaffeinInBody: String?
        get() = prefs.getString("leftCaffeinInBody", "mg" )
        set(value) = prefs.edit().putString("leftCaffeinInBody", value!!).apply()

    var updatedTime: String?
        get() = prefs.getString("updatedTime", "mg" )
        set(value) = prefs.edit().putString("updatedTime", value!!).apply()
}