package sso.hyeon.wachacha

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import sso.hyeon.wachacha.databinding.ActivityMainWearOsBinding
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable


class MainActivityWearOS : ComponentActivity() {
    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }
    private val clientDataViewModel by viewModels<ClientDataViewModel>()

    private lateinit var binding: ActivityMainWearOsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainWearOsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.heartActivityBtn.setOnClickListener{//열기를 누르면?
            val heartRateActivity = HeartRateActivity()
            val intent = Intent(this, heartRateActivity::class.java)
            startActivity(intent)
        }

        binding.caffeineDataBtn.setOnClickListener {
            val intent = Intent(this, sso.hyeon.wachacha.DrinkActivity::class.java)
            startActivity(intent)
        }
    }

    //////////////////////////////////리스너 등록하고 제거하는 부분
    override fun onResume() {
        super.onResume()
        dataClient.addListener(clientDataViewModel)
        messageClient.addListener(clientDataViewModel)
        capabilityClient.addListener(
            clientDataViewModel,
            Uri.parse("wear://"), //스마트폰도 워치도 모두 이 부분을 등록해야 서로의 노드 찾을 수 있음
            CapabilityClient.FILTER_REACHABLE
        )
        Log.e("capability", capabilityClient.toString())
    }

    override fun onPause() {
        super.onPause()
        dataClient.removeListener(clientDataViewModel)
        messageClient.removeListener(clientDataViewModel)
        capabilityClient.removeListener(clientDataViewModel)
    }
}