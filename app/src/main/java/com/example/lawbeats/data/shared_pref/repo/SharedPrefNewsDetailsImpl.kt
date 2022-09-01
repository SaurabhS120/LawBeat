package com.example.lawbeats.data.shared_pref.repo

import android.content.Context
import androidx.core.content.edit
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsDetailsLocalRepo

class SharedPrefNewsDetailsImpl(applicationContext: Context) : NewsDetailsLocalRepo {
    val sp = applicationContext.getSharedPreferences("current_news", Context.MODE_PRIVATE)
    override fun saveNews(news: NewsEntity) {
        sp.edit {
            putString("title", news.title)
            putString("content", news.content)
            putString("imgUrl", news.imgUrl)
            putString("author", news.author)
            putString("time", news.time)
            putString("raw_date", news.raw_date)
            putString("synopsis", news.synopsis)
            putString("news_read_time", news.news_read_time)
        }
    }

    override fun getNews(): NewsEntity {
        val title = sp.getString("title", "") ?: ""
        val content = sp.getString("content", "") ?: ""
        val imgUrl = sp.getString("imgUrl", null)
        val author = sp.getString("author", "") ?: ""
        val time = sp.getString("time", "") ?: ""
        val raw_date = sp.getString("raw_date", "") ?: ""
        val synopsis = sp.getString("synopsis", "") ?: ""
        val news_read_time = sp.getString("news_read_time", "") ?: ""
        return NewsEntity(title, content, imgUrl, author, time, raw_date, synopsis, news_read_time)
    }
}