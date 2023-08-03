package sso.hyeon.caffeinbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import sso.hyeon.caffeinbody.database.DrinksDatabase
import sso.hyeon.caffeinbody.databinding.FragmentSurvey2Binding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Survey1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Survey2Activity  : AppCompatActivity() {
    var question = Array(6, {0})
    private lateinit var db: DrinksDatabase

    private val binding: FragmentSurvey2Binding by lazy {
        FragmentSurvey2Binding.inflate(
            layoutInflater
        )
    }
    var habitnumber = 0
    var awakenumber = 0
    var tastenumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("survey2", "한글깨짐???")
        var caffeine = intent.getDoubleExtra("caffeine",0.0)

        db = DrinksDatabase.getInstance(applicationContext)!!
        //var funcs = CafeDatas()
        /*addDrinksDatabaseStarbucks(db)
        addDrinksDatabaseHalis(db)
        addDrinksDatabaseTwosome(db)
        addDrinksDatabaseEdiya(db)
        addDrinksDatabasePaiks(db)
        addDrinksDatabaseTheVenti(db)
        addDrinksDatabaseGongCha(db)
        addEtc(db)*/


        binding.progressBar.currentStepPosition =1
        binding.checkBox.setOnCheckedChangeListener{compoundButton, b ->
            if(binding.checkBox.isChecked) question[0]=1
            else question[0]=0
            Log.e("ch1","ch1")
        }
        binding.checkBox2.setOnCheckedChangeListener{compoundButton, b ->
            if(binding.checkBox2.isChecked) question[1]=1
            else question[1]=0
        }
        binding.checkBox3.setOnCheckedChangeListener{compoundButton, b ->
            if(binding.checkBox3.isChecked) question[2]=1
            else question[2]=0
        }
        binding.checkBox4.setOnCheckedChangeListener{compoundButton, b ->
            if(binding.checkBox4.isChecked) question[3]=1
            else question[3]=0
        }
        binding.checkBox5.setOnCheckedChangeListener{compoundButton, b ->
            if(binding.checkBox5.isChecked) question[4]=1
            else question[4]=0
        }
        binding.checkBox6.setOnCheckedChangeListener{compoundButton, b ->
            if(binding.checkBox6.isChecked) question[5]=1
            else question[5]=0
        }


        binding.buttonNext.setOnClickListener {
            awakenumber = question[1]+question[3]+question[4]
            habitnumber = question[0]+question[5]
            tastenumber = question[2]

            App.prefs.awakenumber = awakenumber
            App.prefs.habitnumber = habitnumber
            App.prefs.tastenumber = tastenumber

            val selectActivity =  Survey3Activity()
            val intent = Intent(this, selectActivity::class.java)
            intent.putExtra("caffeine", caffeine)
            startActivity(intent)
            finish()
        }

        binding.button5.setOnClickListener{
            val selectActivity =  Survey3Activity()
            val intent = Intent(this, selectActivity::class.java)
            intent.putExtra("caffeine", caffeine)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
    }


    /*inner class CheckBoxListener: View.OnClickListener {
        override fun onClick(v:View?){

            when(v?.id){
                binding.checkBox.getId() -> {
                    if(binding.checkBox.isChecked){
                        question[0] = 1
                    }else question[0] = 0
                    Log.e("checkBox pressed", binding.checkBox.isChecked.toString())
                }
                binding.checkBox2.getId() -> {
                    if(binding.checkBox2.isChecked){
                        question[1] = 1
                    }else question[1] = 0
                }
                binding.checkBox3.getId() -> {
                    if(binding.checkBox3.isChecked){
                        question[2] = 1
                    }else question[2] = 0
                }
                binding.checkBox4.getId() -> {
                    if(binding.checkBox4.isChecked){
                        question[3] = 1
                    }else question[3] = 0
                }
                binding.checkBox5.getId() -> {
                    if(binding.checkBox5.isChecked){
                        question[4] = 1
                    }else question[4] = 0
                }
                binding.checkBox6.getId() -> {
                    if(binding.checkBox6.isChecked){
                        question[5] = 1
                    }else question[5] = 0
                }
                else ->
                Log.e("none", "")
            }
        }
    }*/




}