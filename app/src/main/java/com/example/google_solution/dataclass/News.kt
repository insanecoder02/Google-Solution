package com.example.google_solution.dataclass

data class NewsResponse(
    val articles: List<Article>
)

data class Article(
    val title: String,
    val urlToImage: String,
    val description:String,
    val url:String
)
