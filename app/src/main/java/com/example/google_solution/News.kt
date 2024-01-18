package com.example.google_solution

data class NewsResponse(
    val articles: List<Article>
)

data class Article(
    val title: String,
    val url :String,
    val urlToImage: String
)
