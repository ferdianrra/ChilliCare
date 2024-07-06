package com.dicoding.chillicare.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.chillicare.data.db.HistoryHelper
import com.dicoding.chillicare.databinding.FragmentProfileBinding
import com.dicoding.chillicare.ui.ViewModelFactory
import com.dicoding.chillicare.ui.authorization.WelcomeActivity
import com.dicoding.chillicare.ui.history.HistoryAdapter
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.userData.observe(requireActivity()) {
            binding.apply {
                tvName.text = it.first
                tvEmail.text = it.second
            }
        }

        binding.btnLogOut.setOnClickListener {
            logoutAccount()
        }

        return root
    }

    private fun logoutAccount() {
        deleteAllData()
        profileViewModel.logout()
        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun deleteAllData() {
        lifecycleScope.launch {
            val historyHelper = HistoryHelper.getInstance(requireContext()) // Use requireContext() for fragment
            historyHelper.open()

            try {
                val deletedRowCount = historyHelper.deleteAllData()
                if (deletedRowCount.toString().isEmpty()) {
                    Log.i("ProfileFragment", "Successfully deleted $deletedRowCount waste items")
                  //  adapter.listWaste.clear() // Clear existing data in the adapter
                }
            } finally {
                historyHelper.close() // Ensure database is closed even in case of exceptions
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}