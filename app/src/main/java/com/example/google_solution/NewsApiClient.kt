package com.example.google_solution

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiClient {
//    apikey=pub_366942b7e70d122c76ed9f87e03c3965f1164&q=environment
    private const val BASE_URL = "https://newsdata.io/api/1/news?"
    private const val API_KEY =  "pub_366942b7e70d122c76ed9f87e03c3965f1164"// Replace this with your NewsAPI.org API key

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsService: NewsApiService = retrofit.create(NewsApiService::class.java)

    fun getTopHeadlines(callback: Any) {
//        val call = newsService.getTopHeadlines("us", API_KEY)
//        call.enqueue(callback as Callback<NewsResponse>)
    }
}