package com.dicoding.chillicare.ui.history

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.chillicare.R
import com.dicoding.chillicare.data.db.HistoryHelper
import com.dicoding.chillicare.data.db.MappingHelper
import com.dicoding.chillicare.databinding.ActivityHistoryBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        adapter = HistoryAdapter(this)
        binding.historyRv.layoutManager = LinearLayoutManager(this)
        binding.historyRv.setHasFixedSize(true)
        binding.historyRv.adapter = adapter
        loadHistoryAsync()
    }

    private fun loadHistoryAsync() {
        lifecycleScope.launch {
            val historyHelper = HistoryHelper.getInstance(applicationContext)
            historyHelper.open()
            val deferredHistory = async(Dispatchers.IO) {//async karna kita ingin nilai kembalian dari fungsi yang kita panggil
                val cursor = historyHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val history = deferredHistory.await()
            if (history.size > 0) {
                adapter.listHistory = history
                binding.apply {
                    tvNoActivity.visibility = View.INVISIBLE
                    historyRv.visibility = View.VISIBLE
                }
            } else {
                adapter.listHistory = ArrayList()
                binding.apply {
                    tvNoActivity.visibility = View.VISIBLE
                    historyRv.visibility = View.INVISIBLE
                }
            }
            historyHelper.close()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(BluetoothAdapter.EXTRA_STATE, adapter.listHistory)
    }
}