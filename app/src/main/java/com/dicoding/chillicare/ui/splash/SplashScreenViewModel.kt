package com.dicoding.chillicare.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.chillicare.data.entitity.UserModel
import com.dicoding.chillicare.data.UserRepository

class SplashScreenViewModel (val repository: UserRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}