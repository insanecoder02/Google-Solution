package com.example.google_solution.adapter

// ChatAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.google_solution.R
import com.example.google_solution.dataclass.ChatMessage


class ChatAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val USER_MESSAGE_TYPE = 1
        const val AI_MESSAGE_TYPE = 2
    }

    inner class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userMessageTextView: TextView = itemView.findViewById(R.id.messageTextView)
    }

    inner class AiMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val aiMessageTextView: TextView = itemView.findViewById(R.id.messageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            USER_MESSAGE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sender_message_layout, parent, false)
                UserMessageViewHolder(view)
            }
            AI_MESSAGE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.reccvier_message_layout, parent, false)
                AiMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder.itemViewType) {
            USER_MESSAGE_TYPE -> {
                (holder as UserMessageViewHolder).userMessageTextView.text = message.text
            }
            AI_MESSAGE_TYPE -> {
                (holder as AiMessageViewHolder).aiMessageTextView.text = message.text
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUserMessage) USER_MESSAGE_TYPE else AI_MESSAGE_TYPE
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}

