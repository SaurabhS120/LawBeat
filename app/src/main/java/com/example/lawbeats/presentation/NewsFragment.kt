package com.example.lawbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lawbeats.data.demo.repo.DemoNewsRepo
import com.example.lawbeats.databinding.FragmentNewsBinding
import com.example.app_domain.repo.NewsRepo
import com.example.app_domain.state.NewsApiState
import com.example.lawbeats.presentation.recycler.NewsRecycler
import com.example.lawbeats_retrofit_repo.repo.NewsRetrofitRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFragment : Fragment() {
    //        val repo: NewsRepo = DemoNewsRepo()
    val repo: NewsRepo = NewsRetrofitRepoImpl()

    lateinit var adapter : NewsRecycler
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
        adapter = NewsRecycler()
        binding.newsRecyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch(Dispatchers.IO){
                val data = repo.invoke(uid=66,page=2,tid=tid,items_per_page=10)
                render(data)
            }
        }
        refresh()
        return binding.root
    }
    private fun render(newsApiState: NewsApiState){
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Main){
                when(newsApiState){
                    is NewsApiState.Success ->
                        adapter.submitList(newsApiState.entities)
                    is NewsApiState.Failure -> {
                        Toast.makeText(requireContext(),newsApiState.errorMsg,Toast.LENGTH_SHORT).show()
                    }
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }
    private fun refresh(){
        binding.swipeRefreshLayout.isRefreshing = true
        lifecycleScope.launch(Dispatchers.IO){
            val data = repo.invoke(uid=66,page=2,tid=tid,items_per_page=10)
            render(data)
        }
    }

}