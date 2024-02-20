package com.example.google_solution.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.google_solution.Fragment.NewsShow
import com.example.google_solution.dataclass.Article
import com.example.google_solution.R

class NewsAdapter(private val context: Context, private var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.newTit)
        val img: ImageView = itemView.findViewById(R.id.newsImg)

        init {
            itemView.setOnClickListener {
                val articleUrl = articles[adapterPosition].url
                val fragment = NewsShow().apply {
                    arguments = Bundle().apply {
                        putString("article_url", articleUrl)
                    }
                }
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment_activity_base, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
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
