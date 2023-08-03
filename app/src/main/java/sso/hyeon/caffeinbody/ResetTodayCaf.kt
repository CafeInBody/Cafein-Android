package sso.hyeon.caffeinbody

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ResetTodayCaf : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        App.prefs.weekCafJson+=App.prefs.todayCaf.toString()
        App.prefs.todayCaf = 0f
        Log.e("AlarmCheck", "리셋완료"+App.prefs.todayCaf)
    }

}