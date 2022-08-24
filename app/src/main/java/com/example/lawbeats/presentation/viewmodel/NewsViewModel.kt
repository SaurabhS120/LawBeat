package com.example.lawbeats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_domain.repo.NewsPagingRepo
import com.example.app_domain.repo.NewsRepo
import com.example.lawbeats_retrofit_repo.repo.NewsRetrofitPagingRepo
import com.example.lawbeats_retrofit_repo.repo.NewsRetrofitRepoImpl

class NewsViewModel(tid: Int) : ViewModel() {
    //        val repo: NewsRepo = DemoNewsRepo()
    val repo: NewsRepo = NewsRetrofitRepoImpl()
    val pagingRepo: NewsPagingRepo = NewsRetrofitPagingRepo(repo, tid, viewModelScope)
    val newsFlow = pagingRepo.getPagingFlow()
}