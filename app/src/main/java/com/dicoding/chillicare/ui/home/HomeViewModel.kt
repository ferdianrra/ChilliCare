package com.dicoding.chillicare.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.chillicare.data.entitity.Review
import com.dicoding.chillicare.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _listReview = MutableLiveData<Map<String, Review>>()
    val listReview: LiveData<Map<String, Review>> = _listReview

    init {
        getReview()
    }

    fun getReview() {
        val client = ApiConfig.getApiService().getReviews()
        client.enqueue(object : Callback<Map<String, Review>> {
            override fun onResponse(
                call: Call<Map<String, Review>>,
                response: Response<Map<String, Review>>
            ) {
                if (response.isSuccessful) {
                    val review = response.body()
                    _listReview.value = review ?: emptyMap()
                } else {
                    Log.e(TAG, "onFailure: $response.message()")
                }
            }
            override fun onFailure(call: Call<Map<String, Review>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}