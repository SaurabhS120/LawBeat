package com.example.lawbeats_retrofit_repo.repo

import com.example.app_domain.repo.SearchNewsRepo
import com.example.app_domain.state.NewsApiState
import com.example.lawbeats_retrofit_repo.APIClient
import com.example.lawbeats_retrofit_repo.mapper.NewsMapper

class SearchNewsRetrofitRepo : SearchNewsRepo {
    private val apiClient = APIClient.getApiInterface()
    override suspend fun invoke(keyword: String): NewsApiState {
        try {
            val response = apiClient.searchNews(keyword)
            val entity = NewsMapper.convert(response.body())
            return NewsApiState.Success(entity)
        } catch (e: Exception) {
            return NewsApiState.Failure("Network error")
        }
    }
}