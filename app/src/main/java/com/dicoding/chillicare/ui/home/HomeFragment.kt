package com.dicoding.chillicare.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.chillicare.R
import com.dicoding.chillicare.data.entitity.Review
import com.dicoding.chillicare.databinding.FragmentHomeBinding
import com.dicoding.chillicare.ui.detectplant.DetectPlantActivity
import com.dicoding.chillicare.ui.history.HistoryActivity
import com.dicoding.chillicare.ui.review.ListReviewAdapter
import com.dicoding.chillicare.ui.review.ReviewActivity
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var auth : FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.listReview.observe(requireActivity()) {
            showReview(it)
        }

        viewModel.getReview()
        binding.apply {
            btnAddComment.setOnClickListener {
                startActivity(Intent(requireContext(), ReviewActivity::class.java))
            }
            btnDetectDisease.setOnClickListener {
                startActivity(Intent(requireContext(), DetectPlantActivity::class.java))
            }
            btnHistory.setOnClickListener {
                startActivity(Intent(requireContext(), HistoryActivity::class.java))
            }
            val name = auth.currentUser?.displayName.toString()
            tvWelcome.text = requireContext().getString(R.string.welcome, name)
        }
        return root
    }

    private fun showReview(listReview : Map<String, Review>) {
        binding.rvReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val reviewAdapter =ListReviewAdapter(listReview.values.toList())
        binding.rvReview.adapter = reviewAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReview()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}