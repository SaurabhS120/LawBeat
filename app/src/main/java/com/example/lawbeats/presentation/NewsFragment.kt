package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_domain.entity.NewsEntity
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.lawbeats.databinding.FragmentNewsBinding
import com.example.lawbeats.presentation.recycler.NewsPagingAdapter
import com.example.lawbeats.presentation.viewmodel.NewsViewModel
import com.example.lawbeats.presentation.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFragment() : Fragment() {
    lateinit var newsViewModel: NewsViewModel
    lateinit var adapter: NewsPagingAdapter
    lateinit var binding: FragmentNewsBinding
    var tid: Int = 1
    lateinit var activityInterface: MainActivityInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val activityInterface = requireActivity() as MainActivityInterface
        this.activityInterface = activityInterface
        tid = arguments?.getInt("tab_id") ?: 1
        val newsViewModel: NewsViewModel by viewModels { NewsViewModelFactory(tid) }
        this.newsViewModel = newsViewModel
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        adapter = NewsRecycler()
//        binding.newsRecyclerView.adapter = adapter
        adapter = NewsPagingAdapter(requireContext()) { selectedNews ->
            showDetailedNews(selectedNews)
        }
        binding.newsRecyclerView.adapter = adapter

//        pagingRepo = DemoNewsPagingRepo(DemoNewsRepo(),tid,lifecycleScope)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch(Dispatchers.IO) {
                adapter.refresh()
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState->
                binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
                if (loadState.refresh is LoadState.Error || loadState.refresh is LoadState.Error){
                    Toast.makeText(requireContext(),"Error loading page",Toast.LENGTH_SHORT).show()
                }

            }
        }
        lifecycleScope.launch {
            newsViewModel.newsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
        return binding.root
    }

    fun showDetailedNews(newsEntity: NewsEntity) {
        activityInterface.navigateTo(NavigationDestination.DetailedNewsDestination(newsEntity))
    }
}