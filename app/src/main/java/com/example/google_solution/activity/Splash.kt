package com.example.google_solution.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.google_solution.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.statusBarColor = 0xFF2DCC70.toInt()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, GetStarted::class.java))
        },3000)
        finish()
    }
}