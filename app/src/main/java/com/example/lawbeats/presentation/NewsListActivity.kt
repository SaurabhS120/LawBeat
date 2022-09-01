package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lawbeats.databinding.ActivityNewsListBinding

class NewsListActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val tid: Int = intent.getIntExtra("tab_id", 1)
        val categortName: String =
            intent.extras?.getString("category_name", "News List") ?: "News List"
        title = categortName
        supportFragmentManager.beginTransaction()
            .replace(
                binding.fragmentContainerView.id,
                NewsFragment().apply {
                    arguments = Bundle().apply {
                        // Our object is just an integer :-P
                        putInt("tab_id", tid)
                        putString("tab", categortName)
                    }
                }
            )
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}