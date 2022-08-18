package com.example.lawbeats_retrofit_repo.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsRepo
import com.example.app_domain.state.NewsApiState

class NewsRetrofitPagingSource(
    val backend: NewsRepo,
    val tid:Int
) : PagingSource<Int, NewsEntity>() {
  override suspend fun load(
    params: LoadParams<Int>
  ): LoadResult<Int, NewsEntity> {
    try {
      // Start refresh at page 1 if undefined.
      val nextPageNumber = params.key ?: 1
      val response = backend.invoke(tid = tid, page = nextPageNumber, uid = 66, items_per_page = params.loadSize)
      return when(response){
        is NewsApiState.Success ->{
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
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}