package com.example.google_solution.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivityAuthBinding

class Auth : AppCompatActivity() {
    private lateinit var binding:ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.inBut.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
        }

        binding.upBut.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }
    }
}