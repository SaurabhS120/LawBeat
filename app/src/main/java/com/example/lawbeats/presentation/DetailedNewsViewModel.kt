package com.example.lawbeats.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsDetailsLocalRepo
import com.example.lawbeats.data.shared_pref.repo.SharedPrefNewsDetailsImpl

class DetailedNewsViewModel(application: Application) : AndroidViewModel(application) {
    private var news: NewsEntity? = null
    val newsTitle = MutableLiveData<String>()
    val newsContent = MutableLiveData<String>()
    val newsImageUrl = MutableLiveData<String?>()
    val newsAuthor = MutableLiveData<String>()
    val newsDate = MutableLiveData<String>()
    val newsRawDate = MutableLiveData<String>()

    init {
        val repo: NewsDetailsLocalRepo = SharedPrefNewsDetailsImpl(getApplication())
        val newsEntity = repo.getNews()
        bindNews(newsEntity)
    }

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