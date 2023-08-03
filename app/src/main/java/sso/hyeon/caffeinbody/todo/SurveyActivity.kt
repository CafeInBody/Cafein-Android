package sso.hyeon.caffeinbody.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import sso.hyeon.caffeinbody.R
import sso.hyeon.caffeinbody.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    lateinit var navController: NavController

    private val binding: ActivitySurveyBinding by lazy {
    ActivitySurveyBinding.inflate(
        layoutInflater
    )

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(
            R.id.nav_host_fragment,
            Survey1Fragment(),"home"
        ).commit()
      //  navController = findNavController(R.id.nav_host_fragment)
    }

}