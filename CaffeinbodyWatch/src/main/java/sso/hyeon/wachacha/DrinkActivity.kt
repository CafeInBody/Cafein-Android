package sso.hyeon.wachacha

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import sso.hyeon.wachacha.databinding.ActivityDrinkregisterBinding
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class DrinkActivity: ComponentActivity() {
    private val messageClient by lazy { Wearable.getMessageClient(this) }

    private val binding: ActivityDrinkregisterBinding by lazy {
        ActivityDrinkregisterBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //스마트폰에 데이터 달라고 말하기
        getCaffeineDatas("getdatas")

        val sharedPreferences = getSharedPreferences("caffeinedatas", Context.MODE_PRIVATE)
        val liveSharedPreference = sso.hyeon.wachacha.LiveSharedPreferences(sharedPreferences)
        /*Log.e("DrinkActivity", MainApplication.prefs.drinkedCaffeine_day.toString() )
        Log.e("DrinkActivity", MainApplication.prefs.recommendedCaffeine_day.toString() )
        Log.e("DrinkActivity", MainApplication.prefs.drinkedCaffeine_once.toString() )
        Log.e("DrinkActivity", MainApplication.prefs.recommendedCaffeine_once.toString() )*/
        liveSharedPreference.getString("drinkedCaffeine_day", "*")
            .observe(this, Observer<String>{result->
                binding.drinkedCaffeineDay.text = result
            })

        liveSharedPreference.getString("recommendedCaffeine_day", "")
            .observe(this, Observer<String>{result->
                binding.recommendedCaffeineDay.text = result
            })

        liveSharedPreference.getString("drinkedCaffeine_once", "")
            .observe(this, Observer<String>{result->
                binding.drinkedCaffeineOnce.text = result
            })

        liveSharedPreference.getString("recommendedCaffeine_once", "")
            .observe(this, Observer<String>{result->
                binding.recommendedCaffeineOnce.text = result
            })

        liveSharedPreference.getString("leftCaffeinInBody", "")
            .observe(this, Observer<String>{result->
                binding.leftCaffeine.text = result
            })

        liveSharedPreference.getString("updatedTime", "")
            .observe(this, Observer<String>{result->
                binding.time.text = result
            })



        var favorite = ""
    }


    private fun getNodes(): Collection<String> {
        return Tasks.await(Wearable.getNodeClient(this).connectedNodes).map { it.id }
    }

    fun getCaffeineDatas(watchMessage: String){
        lifecycleScope.launch(Dispatchers.IO) {
            Log.e("노드", getNodes().first())
            try {
                val payload = watchMessage.toByteArray()
                messageClient.sendMessage(
                    getNodes().first(),
                    "/currentInfos",
                    payload
                )
                    .await()
                Log.e("DrinkActivity", "Message sent successfully hahahahah")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.e("sendHeartRate", "Message failed")
            }
        }
    }

}