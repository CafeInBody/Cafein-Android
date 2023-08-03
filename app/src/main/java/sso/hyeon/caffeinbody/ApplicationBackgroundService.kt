package sso.hyeon.caffeinbody

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import sso.hyeon.caffeinbody.HomeFragment.Companion.putCurrentCaffeine
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

//한 번만 실행하게 하기

class ApplicationBackgroundService : Service() {
    val tag = "ForegroundService"
    val CHANNEL_ID2 = "caffeine alarm channel"
    val dataClient by lazy { Wearable.getDataClient(application) }
    val messageClient by lazy { Wearable.getMessageClient(application) }
    val clientDataViewModel =  ClientDataViewModel()

    override fun onCreate() {
        Log.e(tag, "foregroundservice created")
        dataClient.addListener(clientDataViewModel)
        messageClient.addListener(clientDataViewModel)
        Log.e(tag, "foreground에 리스너 등록")
        CoroutineScope(Dispatchers.Main).launch{
            while (App.prefs.isAlarmTrue!!){
                putCurrentCaffeine()
                var rec = App.prefs.sensetivity!!.toFloat()//1회 권장 섭취량
                var cur = App.prefs.currentcaffeine!!.toFloat()!!//실시간 체내 카페인
                var flag = App.prefs.alarmflag

                Log.e(tag, "cur: $cur rec: $rec + flag : $flag")
                if ((cur >rec -1)&&(flag == true)){//158>159 - 1 체내 남은 카페인이 1 미만이면 0으로 초기화 시켜서
                    Log.e(tag, "notification created")
                    createNotification()
                    App.prefs.alarmflag = false
                    //todo notification이 닫히거나 너무 오래 남아있거나
                    Log.e(tag, "notification false")
                }
                Log.e(tag, "반복")
                delay(180000)//30분마다 반복해서
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*if(intent == null){
            Log.e(tag, "null인가 아닌가")
            return START_STICKY
        }//재시작하게

        Log.e(tag, "onstart됨")*/
        //return super.onStartCommand(intent, flags, startId);

        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        dataClient.removeListener(clientDataViewModel)
        messageClient.removeListener(clientDataViewModel)
        Log.e(tag, "service destroyed")
        setAlarmTimer()
    }

    private fun setAlarmTimer() {
        val c: Calendar = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        c.add(Calendar.SECOND, 1)
        val intent = Intent(this, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, sender)


    }

    fun createNotification(){
        //클릭 시 이동
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        //builder
        val builder = NotificationCompat.Builder(this, CHANNEL_ID2)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Cafeinbody 카페인 섭취 알람")
            .setContentText("카페인 등록을 해보세요!")
            .setContentIntent(pendingIntent)

        //채널 생성
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID2,
                "카페인 마시기 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(serviceChannel)
        }

        val notification = builder.build()
        notificationManager.notify(1, notification)
        //notification이 삭제됨
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
