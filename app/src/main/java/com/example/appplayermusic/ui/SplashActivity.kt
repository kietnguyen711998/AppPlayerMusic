package com.example.appplayermusic.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.appplayermusic.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(
            {
                val intent = Intent(
                    this@SplashActivity,
                    HomeActivity::class.java
                )
                startActivity(intent)
                finish()
            }, 1500
        )
    }
}