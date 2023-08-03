
package sso.hyeon.caffeinbody

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import sso.hyeon.caffeinbody.DetailActivity.Companion.calculateCaffeinLeft
import sso.hyeon.caffeinbody.DetailActivity.Companion.minusDays
import sso.hyeon.caffeinbody.databinding.FragmentHomeBinding
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*


class HomeFragment : Fragment() {
    var count = 1
    private val messageClient by lazy { Wearable.getMessageClient(getActivity()) }
    private val nodeClient by lazy { Wearable.getNodeClient(getActivity()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //   initRecycler()
        setUI()
        Log.e("home", "onCreateView")

        //binding.piechart.add


        binding.addBeverageBtn.setOnClickListener{
            activity?.let{
            val selectActivity =  DrinkTypeActivity()
            val intent = Intent(context, selectActivity::class.java)
            startActivity(intent)
        }}
        binding.showDetailText.setOnClickListener{
            activity?.let{
                val selectActivity =  DetailActivity()
                val intent = Intent(context, selectActivity::class.java)
                startActivity(intent)
            }
        }
        binding.myPageBtn.setOnClickListener{
            val recommendActivity =  RecommendActivity()
            val intent = Intent(context, recommendActivity::class.java)
            startActivity(intent)
        }
        binding.checkWatchBtn.setOnClickListener{
            var builder = AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            builder.setTitle("워치에 연동된 앱을 실행합니다")
            builder.setMessage("카페인 섭취 10분 후,\n안정된 상태에서 측정해주세요\n측정이 완료되면 확인 버튼을 눌러주세요")

            // 버튼 클릭시에 무슨 작업을 할 것인가!
            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                startActivity(
                    Intent(activity, HeartrateCalculation::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            })

            builder.show()
            startWearableActivity()
            Log.e("폰에서 워치앱 열기 on HomeFrag", "하자!!!")



        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUI()
        Log.e("home", "resume")
    }

////////////////워치로 열기 버튼 누르면 워치로 메시지 보내서 앱 열게 함
    private fun startWearableActivity() {
        lifecycleScope.launch {
            try {
                val nodes = nodeClient.connectedNodes.await()//노드 검색
                Log.e("nodes: ", nodes[0].toString())//노드 검색은 된다
                // Send a message to all nodes in parallel
                nodes.map { node ->//워치에서 메시지 받게
                    async {
                        messageClient.sendMessage(node.id, "/start-activity3", byteArrayOf())
                            .await()
                    }
                }.awaitAll()

                Log.d(TAG, "Starting activity requests sent successfully")
            } catch (cancellationException: CancellationException) {
                Log.e(TAG, "실패1")
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "실패2")
            }
        }
    }

    private fun setUI(){
        val todayCaf = App.prefs.todayCaf
        binding.intakenCaffeineText.setText(todayCaf.toString())
        App.prefs.dayCaffeine?.let { binding.maximumADayText.setText(it) }

        putCurrentCaffeine()
        val servingsize = App.prefs.currentcaffeine

        var percent = 1.0


        // 상태별 글자 보이게
        if(todayCaf==0f){
            binding.condition.setText("적절한 카페인 섭취는 도움이 됩니다")
            if(App.prefs.remainCafTmp!! >= App.prefs.currentcaffeine!!.toFloat()*0.9){
                binding.condition.setText("잠시 쉬었다 마시는건 어떨까요?")
            }
        }
        else if(App.prefs.remainCafTmp!! >= App.prefs.currentcaffeine!!.toFloat()*0.9){
            binding.condition.setText("잠시 쉬었다 마시는건 어떨까요?")
            if(todayCaf!!>=App.prefs.dayCaffeine!!.toFloat()*0.9 &&
                todayCaf!!<App.prefs.dayCaffeine!!.toFloat()){
                binding.condition.setText("이 정도가 적당해요!\n오늘은 여기까지 마시고 건강을 지키세요")
            }
            else if(todayCaf!!>App.prefs.dayCaffeine!!.toFloat()){
                binding.condition.setText("더 이상은 위험해요!\n카페인 부작용이 건강을 위협하고 있어요")
            }
        }
        else if(todayCaf!!>=App.prefs.dayCaffeine!!.toFloat()*0.9 &&
            todayCaf!!<App.prefs.dayCaffeine!!.toFloat()){
            binding.condition.setText("이 정도가 적당해요!\n오늘은 여기까지 마시고 건강을 지키세요")
        }
        else if(todayCaf!!>App.prefs.dayCaffeine!!.toFloat()){
            binding.condition.setText("더 이상은 위험해요!\n카페인 부작용이 건강을 위협하고 있어요")
        }
        else{
            binding.condition.setText("오늘도 건강한 카페인 섭취를 실천해봐요")
        }

        if(servingsize!= null) {
            binding.AvailableCaffeineText.setText(servingsize)
            percent = servingsize.toDouble() / App.prefs.sensetivity!!.toDouble()
        }else{
            binding.AvailableCaffeineText.setText(App.prefs.sensetivity)
            App.prefs.currentcaffeine = App.prefs.sensetivity
        }
        binding.heart.start()
      //  binding.heart.waveHeightPercent = (percent).toFloat()
        binding.heart.progress = (percent).toFloat()

        binding.sensitivity.text = App.prefs.sensetivity
        if(App.prefs.todayCaf != null && App.prefs.dayCaffeine != null)
             binding.statsProgressbar.progress = (App.prefs.todayCaf!!.toDouble() / App.prefs.dayCaffeine!!.toDouble() * 100).toInt()
        else binding.statsProgressbar.progress = 0
    }

    companion object{
        fun putCurrentCaffeine(){
            val remainCaf = App.prefs.remainCaf
            var nowTime = DetailActivity.getTime()
            var registeredTime = App.prefs.registeredTime//가장 최근 카페인 섭취한 시간
            var halfTime = App.prefs.halftime
            //remainCaf는 체내 잔여 카페인 계산 위해 카페인 등록 이외에 가공되지 않은 값/remainCafTmp는 시간별 계산된 값

            var leftCaffeine = calculateCaffeinLeft(remainCaf!!, nowTime + 24* minusDays() - registeredTime!!, halfTime, 0.5f)
            App.prefs.remainCafTmp = "%.1f".format(leftCaffeine).toFloat()
            //---------------섭취권고량 설정-----------------

            var servingsize = App.prefs.sensetivity?.toDouble()//나의 적정하루 섭취권고량
            Log.e("home", "caffeineLeft: $leftCaffeine, servingSize: $servingsize")
            if (servingsize != null) {
                if((servingsize - leftCaffeine) > 0 ) {
                    var current = servingsize - leftCaffeine
                    App.prefs.currentcaffeine = "%.1f".format(current)
                }
                else App.prefs.currentcaffeine = "0"
            }
            //----------------------------------------

        }
    }




    /*companion object {
        private const val TAG = "HomeFragment"

        private const val START_ACTIVITY_PATH = "/start-activity3"
        private const val FAVORITE_PATH = "/favorite"
        private const val FAVORITE_KEY = "favorite"
    }*/

}