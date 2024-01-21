package com.example.google_solution.Fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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

    private fun addMessageToChat(message: String, isUserMessage: Boolean) {
        val chatMessage = ChatMessage(message, isUserMessage)
        chatAdapter.addMessage(chatMessage)
        binding.chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
    }

}