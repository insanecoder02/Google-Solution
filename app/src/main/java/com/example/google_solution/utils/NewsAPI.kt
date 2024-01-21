package com.example.google_solution.utils

import com.example.google_solution.dataclass.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface NewsAPI {
    @GET("everything")
    fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
    ): Call<NewsResponse>
}

