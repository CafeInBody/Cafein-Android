package sso.hyeon.caffeinbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import sso.hyeon.caffeinbody.database.DrinksDatabase
import sso.hyeon.caffeinbody.databinding.ActivityCaffeineListBinding

class CaffeineListActivity : AppCompatActivity(), View.OnClickListener {
    var arrayString = arrayOf<String>("카페음료","일반음료","기타")
    lateinit var caffeineadapter: CaffeineAdapter
    private lateinit var db: DrinksDatabase
    //var datas = mutableListOf<Drinks>()
    var num2:Int ?=null
    var isitcafe =true
    val clientDataViewModel by viewModels<CaffeineViewModel>()
    val tag = "CaffeineListActivity"

    //livedata는 데이터의 조작이 많은 경우만

    private val binding: ActivityCaffeineListBinding by lazy {
        ActivityCaffeineListBinding.inflate(
            layoutInflater
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("adapter", "크리에이트")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var num= intent.getIntExtra("listnum",1)
        num2 = intent.getIntExtra("listnum",1)
        Log.e(tag, "윗쪽 num: $num")
        //TODO 필터링 누르고 다시 전체화면 볼 수 있는 기능

        setSupportActionBar(binding.toolbar)
        //    supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle((arrayString[num]))

        caffeineadapter = CaffeineAdapter(this, CaffeinCase.LARGE)
        //caffeineadapter.contextParent = activity
        binding.caffeinList.adapter = caffeineadapter
        val layoutManager = GridLayoutManager(this,2)

        db = DrinksDatabase.getInstance(applicationContext)!!

        //caffeineadapter.contextParent= applicationContext

        caffeineadapter.owner = this

        binding.plus.setOnClickListener{
            val intent = Intent(this, PlusDrinkActivity::class.java)
            intent.putExtra("name", num2)
            startActivity(intent)
            finish()

        }
        binding.search.setOnClickListener {

            val intent = Intent(this, SearchDrinksActivity::class.java)
            startActivity(intent)
        }



        if(num==0) {
            isitcafe= true
            binding.starbucks.setOnClickListener(this)
            binding.ediya.setOnClickListener(this)
            binding.twosome.setOnClickListener(this)
            binding.hollys.setOnClickListener(this)
            binding.hollys.setOnClickListener(this)
            binding.paiks.setOnClickListener(this)
            binding.theventi.setOnClickListener(this)
            binding.gongcha.setOnClickListener(this)

            clientDataViewModel.getAll2(true, caffeineadapter)
            caffeineadapter.home = true
            caffeineadapter.madeby = false

        }
        else if(num == 1){
            isitcafe = false
            caffeineadapter.home = true
            binding.coffeeBrand.visibility = View.GONE
            clientDataViewModel.getAll2(false, caffeineadapter)
        }else {
            isitcafe = false
            var string = "해열·진통제"
            db.drinksDao().getDrinkCategory(string).observe(this, Observer {
                caffeineadapter.datas.clear()
                caffeineadapter.datas.addAll(it)
                caffeineadapter.notifyDataSetChanged()
            })
            binding.coffeeBrand.visibility = View.GONE

        }

        binding.caffeinList.layoutManager = layoutManager


    }
    override fun onClick(v: View?) {//observer
        //first = true
        caffeineadapter.home = false
        caffeineadapter.madeby = true

        when(v?.id){
            binding.starbucks.id -> {Log.e("ba", "!!!스")
             //   if(binding.starbucks.isChecked)
                clientDataViewModel.getFilteredMadeby2(isitcafe, "스타벅스", caffeineadapter).toMutableList()
                /*clientDataViewModel.getFilteredMadeby(isitcafe, "스타벅스", caffeineadapter).observe(this, Observer{
                    caffeineadapter.datas.clear()
                    caffeineadapter.datas.addAll(it)
                    caffeineadapter.notifyDataSetChanged()
                    Log.e("hi", it.toString())
                })*/
            }
            binding.ediya.id -> {Log.e("ba", "!!!이")

                clientDataViewModel.getFilteredMadeby2(isitcafe, "이디야", caffeineadapter).toMutableList()
            }
            binding.twosome.id -> {Log.e("ba", "!!!튜")
                clientDataViewModel.getFilteredMadeby2(isitcafe, "투썸플레이스", caffeineadapter).toMutableList()
            }
            binding.hollys.id -> {Log.e("ba", "!!!할")
                clientDataViewModel.getFilteredMadeby2(isitcafe, "할리스", caffeineadapter).toMutableList()
            }
            binding.paiks.id -> {Log.e("ba", "!!!빽")
                clientDataViewModel.getFilteredMadeby2(isitcafe, "빽다방", caffeineadapter).toMutableList()
            }
            binding.theventi.id -> {Log.e("ba", "!!!더")
                clientDataViewModel.getFilteredMadeby2(isitcafe, "더벤티", caffeineadapter).toMutableList()
            }
            binding.gongcha.id -> {
                clientDataViewModel.getFilteredMadeby2(isitcafe, "공차", caffeineadapter).toMutableList()
            }
      }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
     //   menuInflater.inflate(com.example.caffeinbody.R.menu.menu_search,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
     /*   R.id.search-> {
            val intent = Intent(this, SearchDrinksActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.home-> {
            val intent = Intent(this, PlusDrinkActivity::class.java)
            intent.putExtra("name", num2)
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
            true
        }
*/
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}