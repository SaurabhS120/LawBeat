package com.example.lawbeats.data.demo.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsPagingRepo
import com.example.app_domain.repo.NewsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class DemoNewsPagingRepo(val repo: NewsRepo, val tid: Int, val coroutineScope: CoroutineScope) :
    NewsPagingRepo {
    override fun getPagingFlow(): Flow<PagingData<NewsEntity>> {
        val flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10)
        ) {
            DemoNewsPagingSource(repo, tid)
        }.flow
            .cachedIn(coroutineScope)
        return flow
    }
}