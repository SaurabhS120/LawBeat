package com.example.lawbeats.data.demo.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsRepo
import com.example.app_domain.state.NewsApiState

class DemoNewsPagingSource<Repo : NewsRepo>(
    val backend: Repo,
    val tid: Int
) : PagingSource<Int, NewsEntity>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, NewsEntity> {
        try {
            val response = backend.invoke(1, 1, tid, 10)
            when (response) {
                is NewsApiState.Success -> {
                    return LoadResult.Page(
                        data = response.entities,
                        prevKey = null, // Only paging forward.
                        nextKey = null
                    )
                }
                else -> {
                    return LoadResult.Error(Exception("Error in loading dummy page"))
                }
            }
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsEntity>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}