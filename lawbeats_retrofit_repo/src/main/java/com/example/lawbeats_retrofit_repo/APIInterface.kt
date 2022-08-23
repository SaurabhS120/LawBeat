package com.example.lawbeats_retrofit_repo

import com.example.lawbeats_retrofit_repo.model.CatagoriesResponse
import com.example.lawbeats_retrofit_repo.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface APIInterface {
    @GET("lawbeat-news-details")
    suspend fun getNews(
        @Query("uid") uid: Int = 66,
        @Query("page") page: Int = 2,
        @Query("tid") tid: Int = 1,
        @Query("items_per_page") items_per_page: Int = 10
    ): Response<NewsResponse>

    @POST("lawbeat-categories")
    suspend fun getCategories(): Response<CatagoriesResponse>

    @GET("lawbeat-search-list")
    suspend fun searchNews(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1,
        @Query("items_per_page") items_per_page: Int = 10,
        @Query("uid") uid: Int = 66

    ): Response<NewsResponse>
}