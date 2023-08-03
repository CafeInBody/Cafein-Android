package sso.hyeon.caffeinbody

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, RestartServiceForeground::class.java))
            Log.e("AlarmReceiver", "foreground")
        } else {
            context.startService(Intent(context, ApplicationBackgroundService::class.java))
            Log.e("AlarmReceiver", "service")
        }
    }
}