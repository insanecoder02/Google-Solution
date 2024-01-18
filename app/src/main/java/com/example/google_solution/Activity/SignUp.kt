package com.example.google_solution.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
            finish()
        }

        binding.upBut.setOnClickListener {
            startActivity(Intent(this,Home::class.java))
            finish()
        }
    }
}