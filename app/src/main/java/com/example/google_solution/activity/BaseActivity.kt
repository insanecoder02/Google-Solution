package com.example.google_solution.activity

import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
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

//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = resources.getColor(R.color.#2DCC70)

       val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_base)
        navView.setupWithNavController(navController)


        binding.fabButton.setOnClickListener {
            showOptionDialog()
            navigateToTakePictureFragment()
        }
    }

    private fun navigateToTakePictureFragment() {
        val navController = findNavController(R.id.nav_host_fragment_activity_base)
        navController.navigate(R.id.fabButton)
    }

    private fun showOptionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)
        val btncc = dialogView.findViewById<LinearLayout>(R.id.btnCamera)
        val btnGallery = dialogView.findViewById<LinearLayout>(R.id.btnGallery)
        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Choose an option")

        val alertDialog = alertDialogBuilder.create()

        btncc.setOnClickListener {
            // Handle Camera option
            openCamera()
            alertDialog.dismiss()
        }

        btnGallery.setOnClickListener {
            // Handle Gallery option
            openGallery()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun openCamera() {
        // Add your camera logic here
        // For example, you can start a camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(cameraIntent)
    }

    private fun openGallery() {
        // Add your gallery logic here
        // For example, you can start a gallery intent
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivity(galleryIntent)
    }
}