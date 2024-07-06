package com.dicoding.chillicare.ui.authorization.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterViewModel: ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun reigsterFirebase(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest{
                        displayName = name
                    }

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {
                                // Update profil berhasil
                                _isSuccess.value = true
                                _message.value = "Log in successful"
                            } else {
                                // Gagal mengupdate profil
                                _isSuccess.value = false
                                _message.value = profileUpdateTask.exception?.message
                            }
                        }
                } else {
                    _isSuccess.value = false
                    _message.value = task.exception?.message

                }
            }
    }
}