package com.example.nexlesoftapp.network

import com.example.nexlesoftapp.model.UserModel
import com.example.nexlesoftapp.response.CategoriesResponse
import com.example.nexlesoftapp.response.SignupResponse
import retrofit2.Call
import retrofit2.http.*

interface JsonApi {
    @POST("auth/signup")
    fun signupUser(@Body userInfo: UserModel): Call<SignupResponse>

    @GET("categories")
    fun getCategories(
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int
    ): Call<CategoriesResponse>
}