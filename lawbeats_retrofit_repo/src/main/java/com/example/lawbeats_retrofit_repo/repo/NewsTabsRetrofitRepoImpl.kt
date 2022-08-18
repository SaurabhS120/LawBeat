package com.example.lawbeats_retrofit_repo.repo

import com.example.app_domain.entity.NewsTabEntity
import com.example.app_domain.repo.TabsRepo
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats_retrofit_repo.APIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsTabsRetrofitRepoImpl:TabsRepo {
    private val apiClient = APIClient.getApiInterface()
    override suspend fun invoke(): NewsTabApiState {
        return withContext(Dispatchers.IO){
            val response = try{
                apiClient.getCategories()
            }catch (e:Exception){
                return@withContext NewsTabApiState.Failure("Network failure:"+e.message)
            }
            if (response.isSuccessful){
                val catagoriesList = response.body()?.data?.map {
                    NewsTabEntity(it?.id?:"",it?.name?:"")
                }?: listOf()
                return@withContext NewsTabApiState.Success(catagoriesList)
            }
            else{
                return@withContext NewsTabApiState.Failure("Network failure")
            }
        }
    }
}