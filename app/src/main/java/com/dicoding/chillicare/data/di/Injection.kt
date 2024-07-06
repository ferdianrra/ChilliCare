package com.dicoding.chillicare.data.di

import android.content.Context
import com.dicoding.chillicare.data.UserPreferences
import com.dicoding.chillicare.data.UserRepository
import com.dicoding.chillicare.data.datastore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.datastore)
        return UserRepository.getInstance(pref)
    }

}