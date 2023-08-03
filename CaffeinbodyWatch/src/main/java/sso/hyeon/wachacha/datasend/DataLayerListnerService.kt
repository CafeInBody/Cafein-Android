package sso.hyeon.wachacha
import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.*
import kotlinx.coroutines.*
import org.json.JSONObject

class DataLayerListenerService : WearableListenerService() {//wear에서 데이터 영역 이벤트 처리

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)
        dataEvents.forEach { dataEvent ->
            val uri = dataEvent.dataItem.uri
            Log.e("DLLS", "data changed")
            when (uri.path) {
                "/currentInfos"->{
                    var tmp = DataMapItem.fromDataItem(dataEvent.dataItem)
                        .dataMap
                        .getString("caffeineDatas")
                    var jsonObject = JSONObject(tmp)
                    Log.e("DataLayerListnerService", "recieved json: " + jsonObject.toString())
                    MainApplication.prefs.drinkedCaffeine_day = jsonObject.optString("dayDrinked")
                    MainApplication.prefs.recommendedCaffeine_day = jsonObject.optString("dayRecommended") + "mg"
                    MainApplication.prefs.drinkedCaffeine_once = jsonObject.optString("onceDrinkable")
                    MainApplication.prefs.recommendedCaffeine_once = jsonObject.optString("onceRecommended") + "mg"
                    MainApplication.prefs.leftCaffeinInBody = jsonObject.optString("remainCaffineInBody") + "mg"
                    MainApplication.prefs.updatedTime = jsonObject.optString("updatedtime")
                }
                else ->{
                    Log.e("DLLS", "none, path: " + uri.path)
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        val uriHost = messageEvent.sourceNodeId//데이터베이스에 데이터가 없으면 이 uri를 저장하고 받아서 이 nodeid로 메시지 보내기
        when (messageEvent.path) {
            START_ACTIVITY_PATH -> {
                Log.e("DLLS", "액티비티 열기")
                startActivity(
                    Intent(this, HeartRateActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val TAG = "DLLS"

        private const val START_ACTIVITY_PATH = "/start-activity3"
        const val DATA_ITEM_RECEIVED_PATH = "/data-item-received"
        const val IMAGE_PATH = "/image"
        const val IMAGE_KEY = "photo"
        const val FAVORITE_PATH = "/favorite"
        const val FAVORITE_KEY = "favorite"
    }
}
