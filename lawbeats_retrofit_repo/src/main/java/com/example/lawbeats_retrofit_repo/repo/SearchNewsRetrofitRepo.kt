package com.example.lawbeats_retrofit_repo.repo

import com.example.app_domain.repo.SearchNewsRepo
import com.example.app_domain.state.NewsApiState
import com.example.lawbeats_retrofit_repo.APIClient
import com.example.lawbeats_retrofit_repo.mapper.NewsMapper
import com.example.lawbeats_retrofit_repo.mapper.NewsNextPage

class SearchNewsRetrofitRepo : SearchNewsRepo {
    private val apiClient = APIClient.getApiInterface()
    override suspend fun invoke(keyword: String, uid: Int, page: Int, pageSize: Int): NewsApiState {
        try {
            val response = apiClient.searchNews(keyword, page, pageSize, uid)
            val entity = NewsMapper.convert(response.body())
            val nextPage = NewsNextPage.convert(response.body())
            return NewsApiState.Success(entity, nextPage)
        } catch (e: Exception) {
            return NewsApiState.Failure("Network error")
        }
    }
}