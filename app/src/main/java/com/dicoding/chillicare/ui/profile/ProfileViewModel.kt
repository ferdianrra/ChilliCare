package com.dicoding.chillicare.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chillicare.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _userData = MutableLiveData<Pair<String, String>>()
    val userData: LiveData<Pair<String, String>> = _userData
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        getAccount()
    }

    private fun getAccount() {
        val currentUser = auth.currentUser
        val name = currentUser?.displayName.toString()
        val email = currentUser?.email.toString()
        _userData.value = Pair(name, email)
    }

    fun logout() {
        auth.signOut()
        viewModelScope.launch {
            repository.logout()
        }
    }
}

