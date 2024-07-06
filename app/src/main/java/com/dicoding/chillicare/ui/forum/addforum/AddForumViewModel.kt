package com.dicoding.chillicare.ui.forum.addforum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.chillicare.data.network.response.ErrorResponse
import com.dicoding.chillicare.data.network.response.SendForumResponse
import com.dicoding.chillicare.data.network.retrofit.ApiConfig
import com.dicoding.chillicare.ui.forum.ForumViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddForumViewModel: ViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun sendFeedback(title: String, desc: String, date: String) {
        val name = auth.currentUser?.displayName.toString()
        val userId = auth.currentUser?.uid.toString()
        val body = mapOf(
            "userId" to userId,
            "name" to name,
            "title" to title,
            "description" to desc,
            "date" to date
        )
        Log.e(ForumViewModel.TAG, body.toString())
        val client = ApiConfig.getApiService().sendForum(body)
        client.enqueue(object : Callback<SendForumResponse> {
            override fun onResponse(
                call: Call<SendForumResponse>,
                response: Response<SendForumResponse>
            ) {
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    _message.value = "Forum added successfully"
                } else {
                    val gson = Gson()
                    _isSuccess.value = false
                    try {
                        val errorJson = response.errorBody()?.string()
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        Log.e(ForumViewModel.TAG, "Error Message: ${errorResponse.message}")
                        _message.value = errorResponse.message
                    } catch (e: Exception) {
                        Log.e(ForumViewModel.TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SendForumResponse>, t: Throwable) {
                _isSuccess.value = false
                Log.e(ForumViewModel.TAG, "Onfailure: ${t.message}")
                _message.value = t.message
            }

        })
    }


    companion object {
        const val TAG = "AddForumViewModel"
    }
}