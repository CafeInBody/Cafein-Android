package sso.hyeon.wachacha


import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import kotlinx.coroutines.Job

class ClientDataViewModel(
    application: Application
) :
    AndroidViewModel(application),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    private val _events = mutableStateListOf<Event>()

    /**
     * The list of events from the clients.
     */
    val events: List<Event> = _events

    /**
     * The currently received image (if any), available to display.
     */
    var image by mutableStateOf<Bitmap?>(null)
        private set

    private var loadPhotoJob: Job = Job().apply { complete() }
    private var favorite: String = ""

    //데이터항목동기화
    //수신 대기
    //이게 mainapp에 표기되는 내용(그냥 뭘 보내면)
    override fun onDataChanged(dataEvents: DataEventBuffer) {//데이터이벤트버퍼에 무언가 일어나면
        // Add all events to the event log
        _events.addAll(//_events에 추가하기
            dataEvents.map { dataEvent ->
                Log.e("hello", "hi")
                val title = when (dataEvent.type) {//타입이 존재하면 title에 타입 너호
                    DataEvent.TYPE_CHANGED -> R.string.data_item_changed
                    DataEvent.TYPE_DELETED -> R.string.data_item_deleted
                    else -> R.string.data_item_unknown
                }
                Log.e("CDVM", dataEvent.dataItem.toString())
                Event(//map으로 만들어서 _events에 추가하기
                    title = title,
                    text = dataEvent.dataItem.toString()
                )
            }
        )
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {//메시지 받으면
        _events.add(
            Event(
                title = R.string.message,
                text = messageEvent.toString()
            )
        )
        Log.e("events on clientviewmodel", "Events occured")
    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        _events.add(
            Event(
                title = R.string.capability_changed,
                text = capabilityInfo.toString()
            )
        )
    }
}

/**
 * A data holder describing a client event.
 */
data class Event(
    @StringRes val title: Int,
    val text: String
)
