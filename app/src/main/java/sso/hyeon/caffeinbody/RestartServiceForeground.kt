package sso.hyeon.caffeinbody

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class RestartServiceForeground: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification()
        return START_NOT_STICKY


    }

    fun createNotification() {
        Log.e("restart", "시작")
        //클릭 시 이동
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        //builder
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            //
            .setContentText("워치 통신을 위해 백그라운드 작업을 허용합니다")
            .setContentIntent(pendingIntent)

        //채널 생성
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "restart service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            //원래 여기
            notificationManager.createNotificationChannel(serviceChannel)
        }

        val notification = builder.build()
        startForeground(1, notification)
        //왜 NOTIFICATION이 안나오지
        startService(Intent(this, ApplicationBackgroundService::class.java))
        notificationManager.notify(1, notification)
        notificationManager.cancel(1)


        //stopForeground(true)
        //stopSelf()


    }

    companion object {
        private const val NOTIFICATION_DOWNLOAD_ID = 1
        private const val NOTIFICATION_COMPLETE_ID = 2
        private const val CHANNEL_ID = "my_channel"
        private const val CHANNEL_NAME = "default"
        private const val CHANNEL_DESCRIPTION = "This is default notification channel"
    }
}