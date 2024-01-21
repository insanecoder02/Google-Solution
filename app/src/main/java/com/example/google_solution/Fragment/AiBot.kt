package com.example.google_solution.Fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.google_solution.R
import com.example.google_solution.adapter.ChatAdapter
import com.example.google_solution.constants.Constants
import com.example.google_solution.databinding.FragmentAiBotBinding
import com.example.google_solution.dataclass.ChatMessage
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AiBot : Fragment() {

    private var _binding: FragmentAiBotBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatAdapter

    private val CAMERA_REQUEST_CODE = 101
    private val GALLERY_REQUEST_CODE = 102

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiBotBinding.inflate(inflater, container, false)
        return binding.root
    }


    fun receiveImage(imageData: Any?) {
        if (imageData is Uri) {
            Toast.makeText(requireContext(), "Received Image: ${imageData.toString()}", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(),"no image is reccived",Toast.LENGTH_SHORT).show()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val generativeModel = GenerativeModel(
            modelName = Constants.textModel,
            apiKey = Constants.API_KEY_GEMINI
        )

        binding.imageButton.setOnClickListener {
            showOptionDialog()
//            val generativeModel = GenerativeModel(
//                modelName = Constants.imageTextModel,
//                apiKey = Constants.API_KEY_GEMINI
//            )
        }

        chatAdapter = ChatAdapter(mutableListOf())
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRecyclerView.adapter = chatAdapter
        binding.sendButton.setOnClickListener {
            val userMessage = binding.box.text.toString()
            if (userMessage.isNotBlank()) {
                addMessageToChat(userMessage,true)
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        binding.box.text.clear()
                        val inputContent = content {
                            text(userMessage)
                        }
                        val response = generativeModel.generateContent(inputContent)
                        response.text?.let {
                            chatAdapter.addMessage(ChatMessage(it, false))
                        }
                        val chat = generativeModel.startChat(
                            history = listOf(
                                content(role = "user") { text(userMessage) },
                                content(role = "model") { text(response.text!!) }
                            )
                        )
                        chat.sendMessage("How many paws are in my house?")
                    } catch (e: ResponseStoppedException) {
                        addMessageToChat(Constants.privacyPolicyViolationText,false)
                        Log.e("AiBot", "Content generation stopped: $e")
                    }
                }

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showOptionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)
        val btncc = dialogView.findViewById<LinearLayout>(R.id.btnCamera)
        val btnGallery = dialogView.findViewById<LinearLayout>(R.id.btnGallery)
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
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


    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun passImageToFragment(imageData: Any?) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_base)
        if (currentFragment is AiBot) {
            currentFragment.receiveImage(imageData)
        }
    }
    private fun addMessageToChat(message: String, isUserMessage: Boolean) {
        val chatMessage = ChatMessage(message, isUserMessage)
        chatAdapter.addMessage(chatMessage)
        binding.chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
    }

}