package com.dicoding.chillicare.ui.forum

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.chillicare.data.entitity.Forum
import com.dicoding.chillicare.databinding.ItemForumBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForumAdapter(private val listForum: Map<String, Forum>): RecyclerView.Adapter<ForumAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemForumBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val keys = listForum.keys.toList()
        val key = keys[position]
        val forum = listForum[key]

        forum?.let {forum
            holder.apply {
                binding.tvDate.text = displayFormattedDateTime(it.date)
                binding.tvName.text = it.name
                binding.tvDesc.text = it.description
                binding.tvTitle.text = it.title
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(key)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayFormattedDateTime(isoDateTime: String) : String {
        // Definisikan formatter untuk parsing string ISO 8601
        val isoFormatter = DateTimeFormatter.ISO_DATE_TIME

        // Parse string ke LocalDateTime
        val dateTime = LocalDateTime.parse(isoDateTime, isoFormatter)

        // Definisikan formatter untuk format yang diinginkan
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")

        // Format LocalDateTime ke string
        val formattedDateTime = dateTime.format(outputFormatter)

        // Tampilkan hasil dalam TextView
        return formattedDateTime
    }

    override fun getItemCount() = listForum.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(key: String)
    }
}