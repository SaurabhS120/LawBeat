package com.example.lawbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_domain.repo.NewsPagingRepo
import com.example.lawbeats.databinding.FragmentNewsBinding
import com.example.app_domain.repo.NewsRepo
import com.example.lawbeats_retrofit_repo.repo.NewsRetrofitPagingRepo
import com.example.lawbeats.presentation.recycler.NewsPagingAdapter
import com.example.lawbeats_retrofit_repo.repo.NewsRetrofitRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    //        val repo: NewsRepo = DemoNewsRepo()
    val repo: NewsRepo = NewsRetrofitRepoImpl()
    lateinit var pagingRepo: NewsPagingRepo

//    lateinit var adapter : NewsRecycler
    lateinit var adapter : NewsPagingAdapter
    lateinit var binding:FragmentNewsBinding
    var tid:Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        tid = arguments?.getInt("tab_id")?:1
        binding = FragmentNewsBinding.inflate(layoutInflater,container,false)
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
//        adapter = NewsRecycler()
//        binding.newsRecyclerView.adapter = adapter
        adapter = NewsPagingAdapter(requireContext())
        binding.newsRecyclerView.adapter = adapter

        pagingRepo = NewsRetrofitPagingRepo(repo,tid,lifecycleScope)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch(Dispatchers.IO){
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

}