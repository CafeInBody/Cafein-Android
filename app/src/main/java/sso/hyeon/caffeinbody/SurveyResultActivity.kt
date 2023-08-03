package sso.hyeon.caffeinbody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import sso.hyeon.caffeinbody.databinding.ActivitySurveyResultBinding

class SurveyResultActivity : AppCompatActivity() {
    private val binding: ActivitySurveyResultBinding by lazy {
        ActivitySurveyResultBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var caffeine = intent.getDoubleExtra("caffeine", 0.0)


        var age = App.prefs.age
        var isPregnant = App.prefs.isPregnant
        var heartbeat = App.prefs.heartbeat
        var headache = App.prefs.headache

        var recommendDayCaffeine = setRecommendDayCaffeine(age!!, isPregnant).toDouble()

        if (heartbeat==true && headache==true){
            recommendDayCaffeine *= 0.4
            App.prefs.multiply = 0.4f
        }
        else if (heartbeat==true || headache==true){
            recommendDayCaffeine *= 0.7
            App.prefs.multiply = 0.7f
        }else
            App.prefs.multiply = 1f

        Log.e("SurveyResultActivity", App.prefs.multiply.toString())

        if (caffeine >= recommendDayCaffeine)
            caffeine = recommendDayCaffeine
//sensetivity/dayCaffeine/onceCaffeine
        //민감도 곱하는 수치 저장!
        App.prefs.sensetivity = caffeine.toString()

        binding.tvRecommendOnceCaffeine.text = caffeine.toString() + "mg"
        binding.tvRecommendDayCaffeine.text = recommendDayCaffeine.toString() + "mg"

        App.prefs.dayCaffeine = recommendDayCaffeine.toString()
        //개인별 반감기 시간 구하기
        var halftime = calHalfTime(isPregnant)
        Log.e("SurveyResultActivity", "halftime: $halftime")
        App.prefs.halftime = halftime


        binding.buttonCheck.setOnClickListener {
            val selectActivity = MainActivity()
            val intent = Intent(this, selectActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun calHalfTime(preg: Boolean): Float{//민감도에 따른 반감기 시간 계산
        val multiply = App.prefs.multiply
        val basicTime = getString(R.string.basicTime).toInt()
        if (preg == true){
            return 10f
        }else{
            return basicTime * (2 - multiply!!)
        }
    }

    companion object{
        fun setRecommendDayCaffeine(age: String, isPregnant: Boolean): Float{
            var recommendDayCaffeine = 400f
            if (age=="minor" || age=="senior" || isPregnant==true)
                recommendDayCaffeine = 300f
            return recommendDayCaffeine
        }
    }

}