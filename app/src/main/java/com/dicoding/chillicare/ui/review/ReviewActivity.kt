package com.dicoding.chillicare.ui.review

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.dicoding.chillicare.R
import com.dicoding.chillicare.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    private val viewModel: ReviewViewModel by viewModels()
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel.isSuccess.observe(this) {isSuccess ->
            if (isSuccess) {
                showToast(getString(R.string.feedback_completed))
                @Suppress("DEPRECATION")
                onBackPressed()
            }
        }

        binding.btnAddForum.setOnClickListener {
            sendFeedback()
        }
    }

    private fun sendFeedback() {
        binding.apply {
            val name = edAddName.text.toString()
            val rate = edAddRate.text.toString()
            val comment = edAddCommentReview.text.toString()
            if (name.isEmpty() || rate.isEmpty() || comment.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else {
                viewModel.sendFeedback(name, rate, comment)
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}