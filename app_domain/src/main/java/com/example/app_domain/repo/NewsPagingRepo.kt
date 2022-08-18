package com.example.app_domain.repo

import androidx.paging.PagingData
import com.example.app_domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsPagingRepo {
    fun getPagingFlow(): Flow<PagingData<NewsEntity>>
}