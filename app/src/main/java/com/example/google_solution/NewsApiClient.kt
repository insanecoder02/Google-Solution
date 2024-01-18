package com.example.google_solution

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiClient {
    private const val BASE_URL = "https://newsapi.org/v2/top-headlines?category=environment"
    private const val API_KEY =  "efd7144b4c2c4bd496b6723defa72d6e"// Replace this with your NewsAPI.org API key

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsService: ApiInterface = retrofit.create(ApiInterface::class.java)

    fun getTopHeadlines(callback: Any) {
//        val call = newsService.getTopHeadlines("us", API_KEY)
//        call.enqueue(callback as Callback<NewsResponse>)
    }
}