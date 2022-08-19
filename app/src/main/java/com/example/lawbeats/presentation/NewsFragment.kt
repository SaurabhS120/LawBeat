package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsPagingRepo
import com.example.app_domain.repo.NewsRepo
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.lawbeats.data.demo.repo.DemoNewsPagingRepo
import com.example.lawbeats.databinding.FragmentNewsBinding
import com.example.lawbeats.presentation.recycler.NewsPagingAdapter
import com.example.lawbeats_retrofit_repo.repo.NewsRetrofitRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    //        val repo: NewsRepo = DemoNewsRepo()
    val repo: NewsRepo = NewsRetrofitRepoImpl()
    lateinit var pagingRepo: NewsPagingRepo

    lateinit var adapter : NewsPagingAdapter
    lateinit var binding:FragmentNewsBinding
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
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        adapter = NewsRecycler()
//        binding.newsRecyclerView.adapter = adapter
        adapter = NewsPagingAdapter(requireContext()) { selectedNews ->
            showDetailedNews(selectedNews)
        }
        binding.newsRecyclerView.adapter = adapter

        pagingRepo = DemoNewsPagingRepo(repo, tid, lifecycleScope)
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
            pagingRepo.getPagingFlow().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
        return binding.root
    }

    fun showDetailedNews(newsEntity: NewsEntity) {
        activityInterface.navigateTo(NavigationDestination.DetailedNewsDestination(newsEntity))
    }
}