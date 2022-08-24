package com.example.lawbeats_retrofit_repo.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.SearchNewsRepo
import com.example.app_domain.state.NewsApiState

internal class SearchNewsRetrofitPagingSource(
    val backend: SearchNewsRepo,
    val keyword: String
) : PagingSource<Int, NewsEntity>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, NewsEntity> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response =
                backend.invoke(keyword, page = nextPageNumber, pageSize = params.loadSize, uid = 66)
            return when (response) {
                is NewsApiState.Success -> {
                    LoadResult.Page(
                        data = response.entities,
                        prevKey = null, // Only paging forward.
                        nextKey = response.nextPage
                    )
                }
                is NewsApiState.Failure -> {
                    Log.d("Paging Error", response.errorMsg)
                    LoadResult.Error(Exception(response.errorMsg))
                }
            }
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            Log.d("Paging Error", e.message ?: "")
            return LoadResult.Error(Exception("Paging Error:${e.message}"))

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
        Log.d("anchorPosition", state.anchorPosition.toString())
        return state.anchorPosition?.let { anchorPosition ->
            Log.d("anchorPosition", anchorPosition.toString())
            val anchorPage = state.closestPageToPosition(anchorPosition)
            val result = anchorPage?.nextKey
            Log.d("next key in paging", result.toString())
            result
        }
    }
}