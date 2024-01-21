package com.example.google_solution.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.google_solution.R
import com.example.google_solution.dataclass.Shop
import com.google.android.material.imageview.ShapeableImageView

class ShopAdapter(val context: Context, private val shop: List<Shop>) :
    RecyclerView.Adapter<ShopAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tit: TextView = itemView.findViewById(R.id.titleTv)
        val price: TextView = itemView.findViewById(R.id.priceTv)
        val date: TextView = itemView.findViewById(R.id.dateTv)
        val img: ShapeableImageView = itemView.findViewById(R.id.imageTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_shop, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return shop.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val curr = shop[position]

        holder.date.text = curr.timestamp
        holder.tit.text = curr.title
        holder.price.text = curr.price

        Glide.with(context)
            .load(curr.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.img)
    }
}