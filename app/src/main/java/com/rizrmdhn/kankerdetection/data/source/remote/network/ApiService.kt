package com.rizrmdhn.kankerdetection.data.source.remote.network

import com.rizrmdhn.kankerdetection.data.source.remote.response.GetNewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): GetNewsResponse
}