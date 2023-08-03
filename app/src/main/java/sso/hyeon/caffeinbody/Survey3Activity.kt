package sso.hyeon.caffeinbody

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import sso.hyeon.caffeinbody.databinding.FragmentSurvey3Binding

class Survey3Activity  : AppCompatActivity() {
    var heartbeat: Boolean =false
    var headache: Boolean = false
    var quantity: String = ""
    var before: Int = 0

    private val binding: FragmentSurvey3Binding by lazy {
        FragmentSurvey3Binding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var caffeine = intent.getDoubleExtra("caffeine", 0.0)

        binding.progressBar.currentStepPosition =2
        binding.seekBar.setProgress(0)


        //총 곱하는 수치 저장
        binding.buttonNext.setOnClickListener {
//            if (heartbeat==true && headache==true) caffeine *= 0.4
//            else if(heartbeat==true || headache==true) caffeine *= 0.7

            /*val shared = getSharedPreferences("result_survey", Context.MODE_PRIVATE)
            val editor = shared.edit()//sharedpreferences 값 확인해보기
            editor.putString("heartbeat", heartbeat.toString())
            editor.putString("headache", headache.toString())
            editor.putString("quantity", quantity)
            //editor.apply()*/

            App.prefs.heartbeat = heartbeat
            App.prefs.headache = headache

            val selectActivity = SurveyResultActivity()
            val intent = Intent(this, selectActivity::class.java)
            intent.putExtra("caffeine", caffeine)
            startActivity(intent)
            finish()
        }

        binding.button5.setOnClickListener{
            val selectActivity = SurveyResultActivity()
            val intent = Intent(this, selectActivity::class.java)
            intent.putExtra("caffeine",caffeine)
            startActivity(intent)
            finish()
        }

        binding.heartFastLayout.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.button -> heartbeat = true
                R.id.button2 -> heartbeat = false
            }
        }

        binding.headacheLayout.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.button3 -> headache = true
                R.id.button4 -> headache =false
            }
        }

5f
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.seekBar.setProgress((progress / 150) * 150)
                if (progress<100){
                    binding.seekBar.setProgress(0)
                    quantity = 0.toString()}
                else if (progress >= 100 && progress <=200){
                    binding.seekBar.setProgress(150)
                    quantity = 150.toString()}
                else{
                    binding.seekBar.setProgress(300)
                    quantity = 300.toString()}

                Log.e("quantity: ", progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {//변경 하기 위해 터치 시
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {//변경한 후
            }
        })
        

        setContentView(binding.root)
    }





}