package com.dicoding.chillicare.ui.forum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.chillicare.data.entitity.Forum
import com.dicoding.chillicare.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumViewModel : ViewModel() {
    private val _listForum = MutableLiveData<Map<String, Forum>>()
    val listForum: LiveData<Map<String, Forum>> = _listForum

    init {
        getForum()
    }

    fun getForum() {
        val client = ApiConfig.getApiService().getForum()
        client.enqueue(object : Callback<Map<String, Forum>> {
            override fun onResponse(
                call: Call<Map<String, Forum>>,
                response: Response<Map<String, Forum>>
            ) {
                if (response.isSuccessful) {
                    val response = response.body()
                    _listForum.value = response ?: emptyMap()
                } else {
                    Log.e(TAG, "onFailure: $response.message()")
                }
            }

            override fun onFailure(call: Call<Map<String, Forum>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }


    companion object {
        const val TAG = "ForumViewModel"
    }
}