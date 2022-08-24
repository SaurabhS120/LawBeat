package com.example.lawbeats_retrofit_repo.mapper

import android.util.Log
import com.example.lawbeats_retrofit_repo.model.NewsResponse

internal object NewsNextPage {
    fun convert(response: NewsResponse?): Int? {
        val current_page = response?.pager?.first()?.currentPage
        Log.d("current page:", current_page.toString())
        val total_pages = response?.pager?.first()?.totalPages
        Log.d("total pages:", total_pages.toString())
        var nextPage: Int? = null
        if (current_page != null) {
            if (total_pages != null) {
                if ((current_page + 1) <= total_pages) nextPage = current_page + 1
            }
        }
        Log.d("next page:", nextPage.toString())
        return nextPage
    }
}