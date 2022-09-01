package com.example.lawbeats.presentation

import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lawbeats.R
import com.example.lawbeats.databinding.FragmentDetailedNewsBinding
import com.example.lawbeats.presentation.viewmodel.DetailedNewsViewModel

class DetailedNewsActivity : AppCompatActivity() {
    lateinit var binding: FragmentDetailedNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        binding = FragmentDetailedNewsBinding.inflate(layoutInflater, null, false)
        val detailedNewsViewModel: DetailedNewsViewModel by viewModels()
        binding.viewModel = detailedNewsViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detailedNewsViewModel.newsImageUrl.observe(this) {
            if (it != null) {
                Glide.with(this).load(it)
                    .placeholder(R.drawable.news_image)
                    .into(binding.newsImage)
            } else {
                binding.newsImage.setImageResource(R.drawable.news_image)
            }
        }
        detailedNewsViewModel.synopsis.observe(this) {
            val encodedHtml = Base64.encodeToString(it.toByteArray(), Base64.NO_PADDING)
            binding.synopsis.loadData(encodedHtml, "text/html", "base64")
        }
        detailedNewsViewModel.newsContent.observe(this) {
            val encodedHtml = Base64.encodeToString(it.toByteArray(), Base64.NO_PADDING)
            binding.newsContent.loadData(encodedHtml, "text/html", "base64")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}