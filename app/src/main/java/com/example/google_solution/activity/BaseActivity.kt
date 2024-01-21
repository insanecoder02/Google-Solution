package com.example.google_solution.activity

import android.content.Intent
import android.graphics.Bitmap
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
import com.example.google_solution.Fragment.AiBot
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private val CAMERA_REQUEST_CODE = 101
    private val GALLERY_REQUEST_CODE = 102

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
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    passImageToFragment(imageBitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    passImageToFragment(imageUri)
                }
            }
        }
    }

    private fun passImageToFragment(imageData: Any?) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_base)
        if (currentFragment is AiBot) {
            currentFragment.receiveImage(imageData)
        }
    }
}