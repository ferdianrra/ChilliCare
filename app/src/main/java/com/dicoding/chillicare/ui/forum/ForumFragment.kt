package com.dicoding.chillicare.ui.forum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.chillicare.data.entitity.Forum
import com.dicoding.chillicare.databinding.FragmentForumBinding
import com.dicoding.chillicare.ui.forum.addforum.AddForumActivity
import com.dicoding.chillicare.ui.forum.detaill.DetailForumActivity
import com.google.android.play.integrity.internal.k
import org.checkerframework.checker.units.qual.K

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ForumViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listForum.observe(requireActivity()) {
            showForum(it)
        }
        binding?.btnAddForum?.setOnClickListener {
            startActivity(Intent(requireContext(), AddForumActivity::class.java))
        }

        viewModel.getForum()
    }

    private fun showForum(forum: Map<String, Forum>) {
        val sortedForumList = forum.entries.sortedByDescending { it.value.date }
        val sortedForum = sortedForumList.associate { it.key to it.value }
        binding?.rvForum?.layoutManager = LinearLayoutManager(requireContext())
        val forumAdapter = ForumAdapter(sortedForum)
        binding?.rvForum?.adapter = forumAdapter
        forumAdapter.setOnItemClickCallback(object : ForumAdapter.OnItemClickCallback {
            override fun onItemClicked(key: String) {
                Log.e("ForumFragment", key)
                val intent = Intent(requireContext(),DetailForumActivity::class.java)
                intent.putExtra(EXTRA_KEY, key)
                startActivity(intent)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getForum()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTRA_KEY = "extra_key"
    }
}