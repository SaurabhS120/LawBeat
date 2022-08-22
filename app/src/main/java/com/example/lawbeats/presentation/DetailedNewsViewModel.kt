package com.example.lawbeats.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_domain.entity.NewsEntity

class DetailedNewsViewModel : ViewModel() {
    private var news: NewsEntity? = null
    val newsTitle = MutableLiveData<String>()
    val newsContent = MutableLiveData<String>()
    val newsImageUrl = MutableLiveData<String?>()
    val newsAuthor = MutableLiveData<String>()
    val newsDate = MutableLiveData<String>()
    val newsRawDate = MutableLiveData<String>()
    fun bindNews(news: NewsEntity) {
        this.news = news
        newsTitle.postValue(news.title)
        newsContent.postValue(news.content)
        newsImageUrl.postValue(news.imgUrl)
        newsAuthor.postValue(news.author)
        newsDate.postValue(news.time)
        newsRawDate.postValue(news.raw_date)
    }
}