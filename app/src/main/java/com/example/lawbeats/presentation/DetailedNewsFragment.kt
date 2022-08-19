package com.example.lawbeats.presentation

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.lawbeats.R
import com.example.lawbeats.databinding.FragmentDetailedNewsBinding

class DetailedNewsFragment : Fragment() {
    lateinit var binding: FragmentDetailedNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailedNewsBinding.inflate(layoutInflater, container, false)
        val detailedNewsViewModel: DetailedNewsViewModel by activityViewModels()
        binding.viewModel = detailedNewsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        detailedNewsViewModel.newsImageUrl.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(requireContext()).load(it)
                    .placeholder(R.drawable.news_image)
                    .into(binding.newsImage)
            } else {
                binding.newsImage.setImageResource(R.drawable.news_image)
            }
        }
        detailedNewsViewModel.newsContent.observe(viewLifecycleOwner) {
            val encodedHtml = Base64.encodeToString(it.toByteArray(), Base64.NO_PADDING)
            binding.newsContent.loadData(encodedHtml, "text/html", "base64")
        }
        return binding.root
    }
}