package com.example.google_solution.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.google_solution.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }

        binding.upBut.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.pass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                createAccountWithEmailAndPassword(email, password)
                hideKeyboard()
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAccountWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Home::class.java))
                    finish()
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(baseContext, task.exception!!.localizedMessage,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
