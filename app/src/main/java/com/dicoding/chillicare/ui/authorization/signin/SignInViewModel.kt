package com.dicoding.chillicare.ui.authorization.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chillicare.data.entitity.UserModel
import com.dicoding.chillicare.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository): ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _token = MutableLiveData<String>()
    val token : LiveData<String> = _token

    fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    _isSuccess.value = true
                    _token.value = auth.currentUser?.uid
                    _message.value = "Login Successful"
                } else {
                    _isSuccess.value = false
                    _message.value = task.exception?.message

                }
            }

    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }


}