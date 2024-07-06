package com.dicoding.chillicare.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.chillicare.data.network.response.ErrorResponse
import com.dicoding.chillicare.data.network.response.SendReviewsResponse
import com.dicoding.chillicare.data.network.retrofit.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel: ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    fun sendFeedback(name: String, rate: String, comment: String) {
        val body = mapOf(
            "name" to name,
            "rating" to rate,
            "comment" to comment
        )
        val client = ApiConfig.getApiService().sendReviews(body)
        client.enqueue(object : Callback<SendReviewsResponse> {
            override fun onResponse(
                call: Call<SendReviewsResponse>,
                response: Response<SendReviewsResponse>
            ) {
                if (response.isSuccessful) {
                    _isSuccess.value = true
                } else {
                    val gson = Gson()
                    _isSuccess.value = false
                    try {
                        val errorJson = response.errorBody()?.string()
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SendReviewsResponse>, t: Throwable) {
                _isSuccess.value = false
                Log.e(TAG, "Onfailure: ${t.message}")
            }

        })
    }

    companion object {
        const val TAG = "ReviewViewModel"
    }

}