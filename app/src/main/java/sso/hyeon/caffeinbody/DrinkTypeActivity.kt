package sso.hyeon.caffeinbody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import sso.hyeon.caffeinbody.databinding.ActivityDrinkTypeBinding

class DrinkTypeActivity : AppCompatActivity() {
    //음료 타입 선택
    private val binding: ActivityDrinkTypeBinding by lazy {
        ActivityDrinkTypeBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //    supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.caffeBtn.setOnClickListener{nextactivity(0)}
        binding.generalBtn.setOnClickListener {nextactivity(1)}
        binding.etcBtn.setOnClickListener{nextactivity(2)}

    }

    fun nextactivity(num:Int){
        val caffeineListActivity =  CaffeineListActivity()
        val intent = Intent(this, caffeineListActivity::class.java)
        intent.putExtra("listnum",num)
        startActivity(intent)

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