package com.example.lawbeats_retrofit_repo.repo

import com.example.app_domain.repo.TabsRepo
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats_retrofit_repo.APIClient
import com.example.lawbeats_retrofit_repo.mapper.NewsTabsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsTabsRetrofitRepoImpl:TabsRepo {
    private val apiClient = APIClient.getApiInterface()
    override suspend fun invoke(): NewsTabApiState {
        return withContext(Dispatchers.IO){
            val response = try {
                apiClient.getCategories()
            } catch (e: Exception) {
                return@withContext NewsTabApiState.Failure("Network failure:" + e.message)
            }
            if (response.isSuccessful) {
                val tabs = NewsTabsMapper.convert(response.body())
                return@withContext NewsTabApiState.Success(tabs)
            } else {
                return@withContext NewsTabApiState.Failure("Network failure")
            }
        }
    }
}