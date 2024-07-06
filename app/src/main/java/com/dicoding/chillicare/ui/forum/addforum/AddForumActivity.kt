package com.dicoding.chillicare.ui.forum.addforum

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.chillicare.R
import com.dicoding.chillicare.databinding.ActivityAddForumBinding
import com.dicoding.chillicare.ui.forum.ForumViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddForumBinding
    private val viewModel by viewModels<AddForumViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityAddForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.apply {
            isSuccess.observe(this@AddForumActivity) { isSuccess ->
                if (isSuccess) {
                    @Suppress("DEPRECATION")
                    onBackPressed()
                }
            }
            message.observe(this@AddForumActivity) {
                showToast(it)
            }
        }

        binding.btnAddForum.setOnClickListener {
            submitForum()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitForum() {
        val title = binding.edAddTitle.text.toString()
        val desc = binding.edAddDesc.text.toString()
        val currentDateTime = LocalDateTime.now()
        val formattedDate = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
        if (title.isEmpty() || desc.isEmpty()) {
            showToast(getString(R.string.error_empty))
        } else {
            viewModel.sendFeedback(title, desc, formattedDate)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}