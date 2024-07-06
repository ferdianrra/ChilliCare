package com.dicoding.chillicare.ui.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.chillicare.data.entitity.Review
import com.dicoding.chillicare.databinding.ItemReviewBinding

class ListReviewAdapter(private val listReview: List<Review>): RecyclerView.Adapter<ListReviewAdapter.ListViewHolder>()  {

    class ListViewHolder(var binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(name, rate, comment) = listReview[position]
        holder.apply {
            binding.tvComment.text = comment
            binding.tvRating.text = rate
            binding.tvUsername.text = name
        }
    }

    override fun getItemCount(): Int = listReview.size
}