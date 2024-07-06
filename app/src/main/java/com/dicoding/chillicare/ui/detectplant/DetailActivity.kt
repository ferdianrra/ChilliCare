package com.dicoding.chillicare.ui.detectplant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.chillicare.R
import com.dicoding.chillicare.databinding.ActivityDetailBinding
import com.dicoding.chillicare.ui.MainActivity
import com.dicoding.chillicare.ui.detectplant.DetectPlantActivity.Companion.EXTRA_DESC
import com.dicoding.chillicare.ui.detectplant.DetectPlantActivity.Companion.EXTRA_PREVENTION
import com.dicoding.chillicare.ui.detectplant.DetectPlantActivity.Companion.EXTRA_RESULT

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val uri = intent.getStringExtra(EXTRA_URI)?.toUri()
        uri?.let { showImage(it) }

        val myResult = intent.getStringExtra(EXTRA_RESULT)
        val myResultDesc = intent.getStringExtra(EXTRA_DESC)
        val myResultPrevention = intent.getStringExtra(EXTRA_PREVENTION)

        binding.jenisPenyakit.text = myResult
        binding.deskripsiPenyakit.text = myResultDesc
        binding.solutionDesc.text = myResultPrevention
        binding.btnHome.setOnClickListener {
            startActivity(Intent(this@DetailActivity, MainActivity::class.java))
        }
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

    }

    private fun showImage(uri: Uri) {
        binding.ivPlant.setImageURI(uri)
    }
    companion object {
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_URI = "extra_uri"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_PREVENTION = "extra_prevention"
    }
}