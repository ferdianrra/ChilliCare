package com.dicoding.chillicare.ui.forum.detaill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.chillicare.data.network.response.DetailForumResponse
import com.dicoding.chillicare.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailForumViewModel: ViewModel() {

    private val _detailForum = MutableLiveData<DetailForumResponse>()
    val detailForum : LiveData<DetailForumResponse> = _detailForum

    fun getDetail(id:String) {
        val idJson = "${id}.json"
        Log.e(TAG,idJson)
        val client = ApiConfig.getApiService().getDetailForum(idJson)
        client.enqueue(object : Callback<DetailForumResponse> {
            override fun onResponse(
                call: Call<DetailForumResponse>,
                response: Response<DetailForumResponse>
            ) {
                if (response.isSuccessful) {
                    _detailForum.value = response.body()
                } else {
                    Log.e(TAG, "onFailure:${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailForumResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }


    companion object {
        const val  TAG = "DetailForumViewModel"
    }
}