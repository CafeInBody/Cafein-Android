package sso.hyeon.caffeinbody

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import sso.hyeon.caffeinbody.HomeFragment.Companion.putCurrentCaffeine
import com.google.android.gms.wearable.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * A state holder for the client data.
 */
class ClientDataViewModel :
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {
    val tag = "ClientDataViewModel"

    private var _events = mutableStateListOf<Event>()
    /**The list of events from the clients.**/
    val events: List<Event> = _events


    override fun onDataChanged(dataEvents: DataEventBuffer) {
        Log.e("데이터 추가됨", "-------------------------------")
        _events.addAll(
            dataEvents.map { dataEvent ->
                val title = when (dataEvent.type) {
                    DataEvent.TYPE_CHANGED -> R.string.data_item_changed
                    DataEvent.TYPE_DELETED -> R.string.data_item_deleted
                    else -> R.string.data_item_unknown
                }

                Event(
                    title = title,
                    text = dataEvent.dataItem.toString()
                )
            }
        )
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        val uri = messageEvent.path
        when (uri) {
            "/heartrate" -> {
                App.prefs.heartrateAvg = messageEvent.data.decodeToString()
                Log.e(tag, "메시지 왔음: " + App.prefs.heartrateAvg.toString())
            }

            "/currentInfos" -> {
                putCurrentCaffeine()
                var jsonObject = JSONObject()
                jsonObject.put("onceRecommended", App.prefs.sensetivity!!)//1회 권고량
                jsonObject.put("onceDrinkable", App.prefs.currentcaffeine!!)//현재 기준 섭취 가능양
                jsonObject.put("dayRecommended", App.prefs.dayCaffeine!!)//하루 권고량
                jsonObject.put("dayDrinked", App.prefs.todayCaf!!.toString())//남은 카페인 양
                jsonObject.put("remainCaffineInBody", App.prefs.remainCafTmp.toString())
                jsonObject.put("updatedtime", "updated: " + getCurrentTime())
                //Log.e("ClientDataViewModel", jsonObject.toString())
                sendCaffeineDatas(jsonObject.toString())
            }
            else ->{
                Log.e(tag, "none, path: " + uri)
            }
        }



    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        _events.add(
            Event(
                title = R.string.capability_changed,
                text = capabilityInfo.toString()
            )
        )
    }

    private fun sendCaffeineDatas(msg: String) {
        CoroutineScope(Dispatchers.Main).launch{
            val dataClient  = Wearable.getDataClient(App.context())
            try {
                val request = PutDataMapRequest.create("/currentInfos").apply {
                    dataMap.putString("caffeineDatas", msg)
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()
                Log.e("ClientDataViewModel", "DataItem saved: $result")
            } catch (cancellationException: CancellationException) {
                Log.e("ClientDataViewModel", "캔슬됨")
            } catch (exception: Exception) {
                Log.d("ClientDataViewModel", "Saving DataItem failed: $exception")
            }
        }
    }

    fun getCurrentTime(): String{
        val currentTime = Calendar.getInstance().getTime()
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val current: String = format.format(currentTime)

        return current
    }
}

/**
 * A data holder describing a client event.
 */
data class Event(
    @StringRes val title: Int,
    val text: String
)
