package com.dicoding.chillicare.ui.forum.detaill

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.chillicare.R
import com.dicoding.chillicare.data.network.response.DetailForumResponse
import com.dicoding.chillicare.databinding.ActivityDetailForumBinding

class DetailForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailForumBinding
    private val viewModel by viewModels<DetailForumViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.detailForum.observe(this) {
            if (it!=null) {
                showDetail(it)
            }
        }

        val id = intent.getStringExtra(EXTRA_KEY).toString()
        Log.e("DETAILFORUM", id)

        binding.btnHome.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        viewModel.getDetail(id)
    }

    private fun showDetail(it: DetailForumResponse){
        binding.apply {
            tvName.text = it.name
            tvTitle.text = it.title
            tvDesc.text = it.description
        }
    }

    companion object {
        private const val EXTRA_KEY = "extra_key"
    }
}