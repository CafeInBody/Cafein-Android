package sso.hyeon.caffeinbody

import abak.tr.com.boxedverticalseekbar.BoxedVertical
import abak.tr.com.boxedverticalseekbar.BoxedVertical.OnValuesChangeListener
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import sso.hyeon.caffeinbody.DetailActivity.Companion.getDate
import sso.hyeon.caffeinbody.DetailActivity.Companion.getMonth
import sso.hyeon.caffeinbody.DetailActivity.Companion.getTime
import sso.hyeon.caffeinbody.DetailActivity.Companion.getYear
import sso.hyeon.caffeinbody.ReportFragment.Companion.calMonthCaffeineColor
import sso.hyeon.caffeinbody.ReportFragment.Companion.saveMonthCafJson
import sso.hyeon.caffeinbody.database.Drinks
import sso.hyeon.caffeinbody.database.DrinksDatabase
import sso.hyeon.caffeinbody.databinding.ActivityDrinkCaffeineBinding
import kotlinx.coroutines.*
import java.util.*



class DrinkCaffeineActivity : AppCompatActivity() {
    var arrayCaf = arrayOf<Int>(1,2,3)
    var arraySize = arrayOf<Int>(1,2,3)
    val brandshot = hashMapOf( "스타벅스" to 75.0f,"할리스" to 61.0f ,"투썸플레이스" to 88.5f,"이디야" to 103.0f ,"빽다방" to 118.5f,"더벤티" to 107.5f ,"공차" to 80f)
    var shotnum:Int = 0
    var shot = 75.0f
    var resultInt = 100f
    var article : Drinks? = null
    var rangevalue:Int = 100 //샷
    var size:Int = 0

    private lateinit var db: DrinksDatabase

    private val binding: ActivityDrinkCaffeineBinding by lazy{
        ActivityDrinkCaffeineBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //    supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //setContentView(R.layout.activity_drink_caffeine)

        val intent = getIntent()
        val name = intent.getIntExtra("name",0)
        val star = intent.getBooleanExtra("star", false)

        binding.shot.minValue = 0
        binding.shot.maxValue = 12
        binding.shot.wrapSelectorWheel = false
        binding.star.isChecked = star

        db = DrinksDatabase.getInstance(applicationContext)!!

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                article =  db.drinksDao().selectByID(name)
                arrayCaf[0] = article!!.caffeine?.caffeine1!!
                arrayCaf[1] = article!!.caffeine?.caffeine2!!
                arrayCaf[2] = article!!.caffeine?.caffeine3!!
                arraySize[0] = article!!.size?.size1!!
                arraySize[1] = article!!.size?.size2!!
                arraySize[2] = article!!.size?.size3!!
            }.await()
            runOnUiThread {
                initUI(article!!)
                //사이즈별 visibility 수정
                if(article!!.size?.size3 == 0 && (article!!.size?.size1 == 0 || article!!.size?.size2 ==0)) {
                    binding.textView.visibility = GONE
                    binding.radio.visibility = GONE
                }
                else if(article!!.size?.size1 ==0 && article!!.size?.size2 !=0) binding.size1.visibility = GONE
                else if (article!!.size?.size3 ==0 ) binding.size3.visibility = GONE


                binding.shot.value =0

                if(!article!!.iscafe ) binding.cafeShotLayout.visibility =GONE
                if(article!!.category == "해열·진통제") binding.mountlayout.visibility = GONE
                setImg(article!!)
                shot = brandshot.getOrDefault(article?.madeBy, 75.0f)

                if(article!!.caffeine?.caffeine1 != 0)binding.size1.isChecked = true
                else {
                    binding.size2.isChecked = true
                    size =1
                    Log.e("msg", arrayCaf[1].toString()+ "mg")
                }
            }


        }

        //음료 사이즈 선택
        binding.size1.setOnClickListener {
            if(arrayCaf[0] == 0) arrayCaf[0] = arrayCaf[1] * arraySize[0] / arraySize[1]
            resultInt =  arrayCaf[0] * rangevalue /100f + shot*shotnum
            size = 0
            binding.result.setText(resultInt.toString()+"mg")
        }

        binding.size2.setOnClickListener {
            if(arrayCaf[1] == 0) arrayCaf[1] = arrayCaf[0] * arraySize[1] / arraySize[0]
            resultInt = arrayCaf[1] * rangevalue /100f+ shot*shotnum
            size = 1
            binding.result.setText(resultInt.toString()+"mg")
        }

        binding.size3.setOnClickListener {
            if(arrayCaf[2] == 0 && arrayCaf[1] == 0) arrayCaf[2] = arrayCaf[0] * arraySize[2] / arraySize[0]
            else if (arrayCaf[2] == 0) arrayCaf[2] = arrayCaf[1] * arraySize[2] / arraySize[1]
            resultInt = arrayCaf[2] * rangevalue /100f + shot*shotnum
            size = 2
            binding.result.setText(resultInt.toString()+"mg")
        }


        binding.shot.setOnValueChangedListener { numberPicker, i, i2 ->
            shotnum = i2

            Log.e("iiiii",i.toString()+i2)
            if(i - i2 <= 0)
                resultInt += shot
            else resultInt -= shot
            binding.result.setText(resultInt.toString()+"mg")

        }
        //용량 선택
        binding.volume.setOnBoxedPointsChangeListener(object : OnValuesChangeListener {
            override fun onPointsChanged(boxedPoints: BoxedVertical, value: Int) {
                println(value)
                rangevalue = value
                resultInt = value * arrayCaf[size] / 100f + shot * shotnum
                binding.textView7.setText((value.toDouble() / 100).toString()+"잔")
                binding.result.setText(resultInt.toString()+"mg")
            }

            override fun onStartTrackingTouch(boxedPoints: BoxedVertical) {
              //  Toast.makeText(this, "onStartTrackingTouch", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(boxedPoints: BoxedVertical) {
              //  Toast.makeText(this, "onStopTrackingTouch", Toast.LENGTH_SHORT).show()
            }
        })

        // 저장
        binding.save.setOnClickListener {
            saveTime()//카페인 등록 시간 저장
            App.prefs.alarmflag = true//타이머 카운팅 시작
            val msg = App.prefs.todayCaf//하루섭취카페인
            var msg2 = App.prefs.remainCafTmp//체내남은카페인(날짜변경시초기화안됨+시간별계산된버전)
            if (msg != null) {//msg2를 시간별로 계속 업데이트 해야만 가능하다
                msg2= msg2!! + resultInt
                App.prefs.remainCafTmp = msg2
                resultInt = msg + resultInt
            }else{
                App.prefs.remainCafTmp = resultInt
            }
            Log.e("DrinkCaffeine", "msg2!: $msg2 resultInt: $resultInt")
            App.prefs.todayCaf = resultInt
            App.prefs.remainCaf = App.prefs.remainCafTmp
            Log.e("drink: ", "remainCafReal: " + App.prefs.remainCafTmp)

            var todayCaf = App.prefs.todayCaf
            var caffeineColor = calMonthCaffeineColor(todayCaf!!)

            saveMonthCafJson(getDate(), caffeineColor, getMonth())

            if(resultInt>=App.prefs.sensetivity!!.toFloat() || resultInt>=App.prefs.currentcaffeine!!.toFloat()){ // 1회 권장량 이상 섭취하는지 체크
                App.prefs.moreThanSensitivity += "1"
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    fun saveTime(){
        App.prefs.registeredYear = getYear()
        App.prefs.registeredMonth = getMonth()
        App.prefs.registeredDate = getDate()
        App.prefs.registeredTime = getTime()
        Log.e("DrinkCaffeineActivity", App.prefs.registeredDate.toString())
    }

    //뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    fun setImg(article: Drinks){
        if (article.imgurl.startsWith("https:")) {
            Glide.with(this).load(article.imgurl).placeholder(R.drawable.logo)
                .override(500).into(binding.imageViewDrink)

        } else if(article.imgurl.startsWith("$cacheDir/")){

            val bm = BitmapFactory.decodeFile(article.imgurl)
            Glide.with(this).load(bm).placeholder(R.drawable.logo)
                .override(600).into(binding.imageViewDrink)

        }
        else {
            if (article.madeBy == "스타벅스") Glide.with(this)
                .load(R.drawable.starbucks_logo).override(250).into(binding.logo)
            else if (article.madeBy == "이디야") Glide.with(this)
                .load(R.drawable.ediya_logo).override(250).into(binding.logo)
            else if (article.madeBy == "투썸플레이스") Glide.with(this)
                .load(R.drawable.twosome_logo).override(250).into(binding.logo)
            else if (article.madeBy == "할리스") Glide.with(this)
                .load(R.drawable.hollys_logo).override(250).into(binding.logo)
            else if (article.madeBy == "빽다방") Glide.with(this)
                .load(R.drawable.paiks_logo).override(250).into(binding.logo)
            else if (article.madeBy == "더벤티") Glide.with(this)
                .load(R.drawable.theventi_logo).override(250).into(binding.logo)
            else if (article.madeBy == "공차") Glide.with(this)
                .load(R.drawable.gongcha_logo).override(250).into(binding.logo)
            else Glide.with(this).load(R.drawable.logo).into(binding.logo)
            binding.imageViewDrink.visibility = GONE
            binding.logo.visibility = View.VISIBLE
        }


    }
    fun initUI(article: Drinks){
        Log.e("article", article.toString())
        supportActionBar!!.setTitle(article?.drinkName)

        val display = windowManager.defaultDisplay // in case of Activity
/* val display = activity!!.windowManaver.defaultDisplay */ // in case of Fragment
        var devicesize = Point()
        display.getRealSize(devicesize) // or getSize(size)
        binding.volume.layoutParams.height=devicesize.x *355/1000

    }
    fun selectByID(db: DrinksDatabase, find: Int){
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
               article =  db.drinksDao().selectByID(find)
            }.await()

        }
    }



}

