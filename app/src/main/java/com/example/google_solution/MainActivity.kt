package com.example.google_solution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.google_solution.R
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyDYYXjFiVrR6T82zmCPvipQJBnJ9ls_J78"
        )

        val promptEditText: EditText = findViewById(R.id.promptEditText)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val generateButton: Button = findViewById(R.id.generateButton)

        generateButton.setOnClickListener {
            val prompt = promptEditText.text.toString()

            lifecycleScope.launch {
                val response = generativeModel.generateContent(prompt)
                val generatedContent = response.text

                // Update the TextView with the generated content
                resultTextView.text = generatedContent
            }
        }
    }
}

