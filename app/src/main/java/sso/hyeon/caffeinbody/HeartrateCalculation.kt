package sso.hyeon.caffeinbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import sso.hyeon.caffeinbody.SurveyResultActivity.Companion.setRecommendDayCaffeine
import sso.hyeon.caffeinbody.databinding.ActivityHeartrateBinding

class HeartrateCalculation : AppCompatActivity() {
    val tag = "HeartrateCalculation"
    private val binding: ActivityHeartrateBinding by lazy {
        ActivityHeartrateBinding.inflate(
            layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getResultBtn.setOnClickListener {
            var normalHeartRate = binding.normalHeartrateEditText.text.toString()

            if (normalHeartRate == ""){
                Log.e(tag, "심박수 입력 안함")
            }else if(normalHeartRate.toFloat() <= 50){
                Log.e(tag, "심박수가 너무 낮음")
            }else{
                Log.e(tag, "입력한 평균 심박수: " + normalHeartRate)
                App.prefs.normalHeartRate = normalHeartRate.toFloat()
                getResult(normalHeartRate.toFloat())
            }
        }

        binding.buttonCheck.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        var heartrate = App.prefs.heartrateAvg
        var normalHeartrate = App.prefs.normalHeartRate.toString()
        Log.e(tag, "hi")
        if (heartrate !== null){
            binding.heartrateTV.setText(heartrate)
            binding.currentTxt.visibility = GONE
        }
        if (normalHeartrate !== null){
            binding.normalHeartrateEditText.setText(normalHeartrate)
        }

        super.onResume()
    }

    fun getResult(normalHeartrate: Float){
        var heartrate = App.prefs.heartrateAvg?.toFloat()
        if (heartrate == null){
            Log.e(tag, "prefs에 빈 값")
        }else{
            var heartrateGap = heartrate - normalHeartrate!!

            if (heartrateGap>0){
                Log.e(tag, "heartrateGap: $heartrateGap")
                var recommendDayCaffeine = setRecommendDayCaffeine(App.prefs.age!!, App.prefs.isPregnant)

                if (heartrateGap>=10){
                    val dayCaffeine = recommendDayCaffeine * 0.7
                    App.prefs.dayCaffeine = dayCaffeine.toString()
                }else{
                    val dayCaffeine = recommendDayCaffeine
                    App.prefs.dayCaffeine = dayCaffeine.toString()
                }
                binding.tvRecommendDayCaffeine.setText(App.prefs.dayCaffeine)
                binding.tvRecommendOnceCaffeine.setText(App.prefs.sensetivity)
                binding.resultLayout.visibility = View.VISIBLE

            }else{
                val textView = TextView(this)
                textView.text = "현재 평온한 상태입니다.\n기존 권장섭취량에 맞춰 카페인을 섭취하세요"
                binding.linearLayout6.addView(textView)
            }
        }
    }
    //심박수 결과 받으면(평균 심박수 입력)
}