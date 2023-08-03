package sso.hyeon.caffeinbody

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mainintent: Intent
        if (App.prefs.sensetivity != null && App.prefs.dayCaffeine != null) {
            mainintent = Intent(this@SplashActivity, MainActivity::class.java)
        } //자동 로그인
        else {
            mainintent = Intent(this@SplashActivity, Survey1Activity::class.java)
        }
        Handler().postDelayed(Runnable {
            startActivity(mainintent)
            finish()
        }, 1000)
    }
}