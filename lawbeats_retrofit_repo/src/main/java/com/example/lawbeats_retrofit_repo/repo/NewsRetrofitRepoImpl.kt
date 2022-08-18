package com.example.lawbeats_retrofit_repo.repo

import com.example.app_domain.repo.NewsRepo
import com.example.app_domain.state.NewsApiState
import com.example.lawbeats_retrofit_repo.mapper.NewsMapper
import com.example.lawbeats_retrofit_repo.APIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRetrofitRepoImpl: NewsRepo {
    private val apiClient = APIClient.getApiInterface()
    override suspend fun invoke(uid:Int,page:Int,tid:Int,items_per_page:Int): NewsApiState {
        return withContext(Dispatchers.IO){
            val response = try{
                apiClient.getNews(uid, page, tid, items_per_page)
            }catch (e:Exception){
                return@withContext NewsApiState.Failure("Network failure:"+e.message)
            }
            if (response.isSuccessful){
                val entities = NewsMapper.convert(response.body())
                return@withContext NewsApiState.Success(entities)
            }
            else{
                return@withContext NewsApiState.Failure("Network failure")
            }
        }
    }
}