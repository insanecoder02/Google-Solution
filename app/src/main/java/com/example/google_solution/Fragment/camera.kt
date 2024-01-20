package com.example.google_solution.Fragment
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.google_solution.R
//import kotlinx.android.synthetic.main.fragment_camera.*
import android.provider.MediaStore
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.google_solution.adapter.OptionsAdapter
import java.io.InputStream

class camera : Fragment() {

    private val CAMERA_REQUEST_CODE = 100
    private val GALLERY_REQUEST_CODE = 101
    private  lateinit var imageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById<ImageView>(R.id.imageView3)

        // Show the dialog to choose between camera and gallery
        showOptionsDialog()
    }

    private fun showOptionsDialog() {
        val options = arrayOf("Camera", "Gallery")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose an option")

        // Create a list of items for the ArrayAdapter
        val optionList = ArrayList<View>()

        // Add two options with icons to the list
        for (i in options.indices) {
            val optionView = LayoutInflater.from(requireContext()).inflate(R.layout.grid_item_layout, null)

            // Set icon and text
            val iconImageView = optionView.findViewById<ImageView>(R.id.iconImageView)
            val optionTextView = optionView.findViewById<TextView>(R.id.optionTextView)

            // Replace with your actual icon resources
            iconImageView.setImageResource(getIconResourceId(i))
            optionTextView.text = options[i]

            // Add optionView to the list
            optionList.add(optionView)
        }

        // Create ArrayAdapter using the list of options
        val adapter = ArrayAdapter(requireContext(), R.layout.grid_item_layout, optionList)

        // Set the ArrayAdapter to the AlertDialog
        builder.setAdapter(adapter) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }

        builder.show()
    }

    private fun getIconResourceId(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_launcher_background // Replace with your actual camera icon resource
            1 -> R.drawable.ic_launcher_background // Replace with your actual gallery icon resource
            else -> R.drawable.ic_launcher_foreground
        }
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

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    // Photo captured from camera
                    val photo = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(photo)
                }
                GALLERY_REQUEST_CODE -> {
                    // Photo selected from gallery
                    data?.data?.let { uri ->
                        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}
