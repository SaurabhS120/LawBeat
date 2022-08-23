package com.example.lawbeats.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsDetailsLocalRepo
import com.example.app_domain.repo.SearchNewsPagingRepo
import com.example.app_domain.repo.SearchNewsRepo
import com.example.lawbeats.data.shared_pref.repo.SharedPrefNewsDetailsImpl
import com.example.lawbeats.databinding.ActivitySearchBinding
import com.example.lawbeats.databinding.FragmentNewsBinding
import com.example.lawbeats.presentation.recycler.NewsPagingAdapter
import com.example.lawbeats_retrofit_repo.repo.SearchNewsPagingRetrofitRepoImpl
import com.example.lawbeats_retrofit_repo.repo.SearchNewsRetrofitRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity() {
    lateinit var newsLocalRepo: NewsDetailsLocalRepo

    //            val repo: NewsRepo = DemoNewsRepo()
    val repo: SearchNewsRepo = SearchNewsRetrofitRepo()
    lateinit var pagingRepo: SearchNewsPagingRepo

    lateinit var adapter: NewsPagingAdapter
    lateinit var binding: FragmentNewsBinding
    var tid: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        newsLocalRepo = SharedPrefNewsDetailsImpl(applicationContext)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.backButton.setOnClickListener {
            if (binding.searchEdittext.text.isEmpty()) {
                finish()
            } else {
                binding.searchEdittext.text.clear()
            }
        }
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = NewsPagingAdapter(this) { selectedNews ->
            showDetailedNews(selectedNews)
        }
        binding.newsRecyclerView.adapter = adapter
        pagingRepo = SearchNewsPagingRetrofitRepoImpl(repo, "", lifecycleScope)
//        pagingRepo = NewsRetrofitPagingRepo(repo, tid, lifecycleScope)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch(Dispatchers.IO) {
                adapter.refresh()
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
                if (loadState.refresh is LoadState.Error || loadState.refresh is LoadState.Error) {
                    Toast.makeText(this@SearchActivity, "Error loading page", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
        var job: Job? = null
        binding.searchEdittext.doOnTextChanged { text, start, before, count ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                searchNews(text.toString())
            }
        }

    }

    private suspend fun searchNews(keyword: String) {
        pagingRepo = SearchNewsPagingRetrofitRepoImpl(repo, keyword, lifecycleScope)
        pagingRepo.getPagingFlow().collectLatest { pagingData ->
            adapter.submitData(pagingData)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDetailedNews(newsEntity: NewsEntity) {
        newsLocalRepo.saveNews(newsEntity)
        startActivity(Intent(this, DetailedNewsActivity::class.java))
    }

}