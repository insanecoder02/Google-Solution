package com.example.google_solution.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.google_solution.dataclass.Article
import com.example.google_solution.R

class NewsAdapter(private val context: Context, private var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.newTit)
        val img: ImageView = itemView.findViewById(R.id.newsImg)
    }
    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.description.text = article.title

        Glide.with(context)
            .load(article.urlToImage)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}