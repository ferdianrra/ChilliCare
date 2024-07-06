package com.dicoding.chillicare.ui.history

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.chillicare.R
import com.dicoding.chillicare.data.entitity.History
import com.dicoding.chillicare.databinding.ItemHistoryBinding

class HistoryAdapter(private val activity: Activity) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var listHistory = ArrayList<History>()
        set(listNotes) {
            field.clear()
            field.addAll(listNotes)
            notifyDataSetChanged()  // Notify adapter of data change
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int = listHistory.size

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryBinding.bind(itemView)

        fun bind(historyDisease: History) {
            binding.tvDiseaseName.text = historyDisease.disease
            binding.tvItemDate.text = historyDisease.date
            binding.tvDiseaseDescription.text = historyDisease.descDisease
            binding.diseasePhoto.setImageURI(historyDisease.photoLeaf.toUri())
        }
    }


}
