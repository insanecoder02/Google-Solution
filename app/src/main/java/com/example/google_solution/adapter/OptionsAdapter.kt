package com.example.google_solution.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.google_solution.R

class OptionsAdapter(private val context: Context, private val options: Array<String>) :
    RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val optionTextView: TextView = itemView.findViewById(R.id.optionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.iconImageView.setImageResource(getIconResourceId(position))
        holder.optionTextView.text = options[position]
    }

    override fun getItemCount(): Int {
        return options.size
    }

    private fun getIconResourceId(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_launcher_foreground
            1 -> R.drawable.ic_launcher_foreground
            else -> R.drawable.ic_launcher_foreground
        }
    }
}
