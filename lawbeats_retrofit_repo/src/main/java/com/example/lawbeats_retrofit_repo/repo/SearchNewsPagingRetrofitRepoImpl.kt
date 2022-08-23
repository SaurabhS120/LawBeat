package com.example.lawbeats_retrofit_repo.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.SearchNewsPagingRepo
import com.example.app_domain.repo.SearchNewsRepo
import com.example.lawbeats_retrofit_repo.paging_source.SearchNewsRetrofitPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchNewsPagingRetrofitRepoImpl(
    val repo: SearchNewsRepo,
    val keyword: String,
    val coroutineScope: CoroutineScope
) :
    SearchNewsPagingRepo {
    override fun getPagingFlow(): Flow<PagingData<NewsEntity>> {
        val flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10)
        ) {
            SearchNewsRetrofitPagingSource(repo, keyword)
        }.flow
            .cachedIn(coroutineScope)
        return flow
    }
}