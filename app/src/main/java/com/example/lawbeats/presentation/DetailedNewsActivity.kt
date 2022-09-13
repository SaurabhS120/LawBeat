package com.example.lawbeats.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import androidx.webkit.WebViewFeature
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
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    WebSettingsCompat.setForceDark(binding.synopsis.settings, FORCE_DARK_ON)
                    WebSettingsCompat.setForceDark(binding.newsContent.settings, FORCE_DARK_ON)
                }
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    WebSettingsCompat.setForceDark(binding.synopsis.settings, FORCE_DARK_OFF)
                    WebSettingsCompat.setForceDark(binding.newsContent.settings, FORCE_DARK_OFF)
                }
                else -> {
                    //
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}