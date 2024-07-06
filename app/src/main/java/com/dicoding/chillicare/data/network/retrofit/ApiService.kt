package com.dicoding.chillicare.data.network.retrofit

import com.dicoding.chillicare.data.entitity.Forum
import com.dicoding.chillicare.data.entitity.Review
import com.dicoding.chillicare.data.network.response.DetailForumResponse
import com.dicoding.chillicare.data.network.response.SendForumResponse
import com.dicoding.chillicare.data.network.response.SendReviewsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("forum.json")
    fun sendForum(@Body requestBody: Map<String, String>): Call<SendForumResponse>

    @GET("forum.json")
    fun getForum(): Call <Map<String, Forum>>

    @POST("review.json")
    fun sendReviews(@Body requestBody: Map<String, String>): Call<SendReviewsResponse>

    @GET("review.json")
    fun getReviews(): Call <Map<String, Review>>

    @GET("forum/{id}")
    fun getDetailForum(@Path("id") id: String): Call <DetailForumResponse>
}