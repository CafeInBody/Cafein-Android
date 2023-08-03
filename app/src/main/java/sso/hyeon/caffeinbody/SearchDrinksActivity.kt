package sso.hyeon.caffeinbody

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import sso.hyeon.caffeinbody.database.Drinks
import sso.hyeon.caffeinbody.database.DrinksDatabase
import sso.hyeon.caffeinbody.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchDrinksActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var db: DrinksDatabase
    lateinit var caffeineadapter: CaffeineAdapter
    var datas = mutableListOf<Drinks>()
    var madeby = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        caffeineadapter = CaffeineAdapter(this,CaffeinCase.LARGE)
        binding.caffeinList.adapter = caffeineadapter

        val itemList = listOf("제조사를 선택하세요", "스타벅스", "이디야", "투썸플레이스", "할리스", "빽다방", "더벤티", "공차")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, itemList)
        binding.spinner1.adapter = adapter
        binding.spinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0->{
                        madeby = ""
                    }
                    1->{
                        madeby = "스타벅스"
                    }
                    2->{
                        madeby = "이디야"
                    }
                    3->{
                        madeby = "투썸플레이스"
                    }
                    4->{
                        madeby = "할리스"
                    }
                    5->{
                        madeby = "빽다방"
                    }
                    6->{
                        madeby = "더벤티"
                    }
                    7->{
                        madeby = "공차"
                    }
                    else-> Log.e("SearchDrinksActivity", "error")
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {            }        }

        binding.searchBtn.setOnClickListener {
            var name = binding.editText2.text.toString()
          //  var cat = binding.editText4.text.toString()
            var cat = ""
            db = DrinksDatabase.getInstance(applicationContext)!!
            selectIntersect(db, name, madeby, cat)
        }

        val layoutManager = GridLayoutManager(this,2)
        binding.caffeinList.layoutManager = layoutManager
    }

    fun selectIntersect(db: DrinksDatabase, name: String, madeby: String, category: String){
        CoroutineScope(Dispatchers.Main).launch {
            val drinkNames = CoroutineScope(Dispatchers.IO).async {
                db.drinksDao().selectIntersect("%$name%","%$madeby%","%$category%")
            }.await()

            for (names in drinkNames){
                Log.e("NameNew", "selectDrinkName: " + names)
              //  datas.add(CaffeineData(1, names.id, names.drinkName, 0) )
                datas.add(names)
            }
            Log.e("datas", datas.count().toString())
            initRecycler()
            datas.clear()
        }
    }

    private fun initRecycler(){
        caffeineadapter.datas.clear()
        caffeineadapter.datas.addAll(datas)
        caffeineadapter.notifyDataSetChanged()
    }

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
}