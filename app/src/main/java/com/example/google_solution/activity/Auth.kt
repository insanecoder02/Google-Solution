package com.example.google_solution.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.google_solution.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth

class Auth : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = 0xFF2DCC70.toInt()

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already signed in, skip the sign-in part
            startActivity(Intent(this, BaseActivity::class.java))
        }
        binding.inBut.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
        binding.upBut.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}
