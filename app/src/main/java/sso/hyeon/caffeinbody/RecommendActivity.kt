package sso.hyeon.caffeinbody

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.baoyz.widget.PullRefreshLayout
import sso.hyeon.caffeinbody.database.DrinksDatabase
import sso.hyeon.caffeinbody.databinding.ActivityRecommendBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RecommendActivity : AppCompatActivity() {
    private lateinit var db: DrinksDatabase
    lateinit var caffeineadapter: CaffeineAdapter
    lateinit var caffeineadapternon: CaffeineAdapter
    var caffeine = App.prefs.currentcaffeine?.toDouble()

    private val binding: ActivityRecommendBinding by lazy{
        ActivityRecommendBinding.inflate(
            layoutInflater
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //    supportActionBar!!.setDisplayShowTitleEnabled(false)

        caffeineadapter = CaffeineAdapter(this, CaffeinCase.SMALL)
        caffeineadapternon = CaffeineAdapter(this, CaffeinCase.SMALL)
        caffeineadapter.owner = this
        caffeineadapternon.owner = this

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.swipe.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP)
        binding.swipe.size
        binding.swipe.setColorSchemeColors(Color.parseColor("#9D5812"),Color.parseColor("#9D5812"),Color.parseColor("#F8786F"),Color.parseColor("#9D5812"))


        updateUI()
        //    caffeineadapter.type = CaffeinCase.SMALL

        //    val r = Runnable {
        //      try {

        //    val servingsize = App.prefs.currentcaffeine

        binding.swipe.setOnRefreshListener {
            updateUI()
            binding.swipe.setRefreshing(false)
        }
    }

    fun updateUI(){


        CoroutineScope(Dispatchers.IO).launch {

            db = DrinksDatabase.getInstance(applicationContext)!!

            var nondatas = db.drinksDao().recommendnoncaffeine("논카페인")
            var datas = caffeine?.let { db.drinksDao().recommendcaffeine(it) }
         //   Log.e("data",db.drinksDao().recommendnoncaffeine().toString())
            caffeineadapter.datas.clear()
            caffeineadapternon.datas.clear()
            if (datas?.size != 0 && datas != null) {
                datas.let {
                    //데이터가 많아서 랜덤으로 6개만 뽑아서 보여줌
                    var uilist = it.shuffled() //데이터 섞기
                    for (i in 0 until 6) { //6개만 뽑아서 넣음
                        if (uilist.size <= i) break
                        caffeineadapter.datas.add(uilist[i])
                    }}
                    //caffeineadapter.datas.addAll(it)
                  }
            nondatas.let {
                var reclist = it.shuffled() //데이터 섞기
                for (i in 0 until 3) { //6개만 뽑아서 넣음
                    if (reclist.size <= i) break

                    caffeineadapternon.datas.add(reclist[i])

                }
                //caffeineadapter.datas.addAll(it)

            }
                    runOnUiThread {
                        caffeineadapter.notifyDataSetChanged()
                        caffeineadapternon.notifyDataSetChanged()
                        if (caffeine != 0.0){
                            binding.nonetext.visibility = GONE //데이터 없을때 나오는 텍스트
                            binding.recyclerView.visibility = VISIBLE
                        }
                        // call the invalidate()
                    }
                }






        binding.recyclerView.adapter = caffeineadapter


        binding.recyclerView2.adapter = caffeineadapternon
        caffeineadapternon.notifyDataSetChanged()

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