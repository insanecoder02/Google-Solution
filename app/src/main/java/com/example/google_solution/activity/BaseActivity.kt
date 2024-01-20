package com.example.google_solution.activity

import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = 0xFF2DCC70.toInt()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_base)
        navView.setupWithNavController(navController)


        binding.button.setOnClickListener {
            // Handle FAB button click
            navigateToTakePictureFragment()
        }
    }

    private fun navigateToTakePictureFragment() {
        val navController = findNavController(R.id.nav_host_fragment_activity_base)
        // Assuming you have a navigation graph with an action to navigate to the picture fragment
        navController.navigate(R.id.button)
    }
}