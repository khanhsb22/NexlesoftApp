package com.example.nexlesoftapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.nexlesoftapp.model.UserModel
import com.example.nexlesoftapp.network.JsonApi
import com.example.nexlesoftapp.response.CategoriesResponse
import com.example.nexlesoftapp.response.SignupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val jsonApi: JsonApi) {
    private val signupResponse = MutableLiveData<SignupResponse>()
    private var categoriesResponse: MutableLiveData<CategoriesResponse> =
        MutableLiveData<CategoriesResponse>()
    private val TAG = "MainRepository"

    fun signupUser(userInfo: UserModel): MutableLiveData<SignupResponse> {
        GlobalScope.launch(Dispatchers.IO) {
            var call = jsonApi.signupUser(userInfo)
            call.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: Signup response: $response")
                        signupResponse.postValue(response.body())
                    } else {
                        signupResponse.postValue(null)
                        Log.e(TAG, "onResponse: Cannot signup user !")
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: Error signup user: ${t.message.toString()}")
                    signupResponse.postValue(null)
                }
            })
        }
        return signupResponse
    }

    fun getCategories(pageSize: Int, pageNumber: Int): MutableLiveData<CategoriesResponse> {
        GlobalScope.launch(Dispatchers.IO) {
            var call = jsonApi.getCategories(pageSize, pageNumber)
            call.enqueue(object : Callback<CategoriesResponse> {
                override fun onResponse(
                    call: Call<CategoriesResponse>,
                    response: Response<CategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: ${response.body().toString()}")
                        categoriesResponse.postValue(response.body())
                    } else {
                        categoriesResponse.postValue(null)
                        Log.e(
                            TAG,
                            "onResponse: Cannot get categories ${response.body().toString()}!"
                        )
                    }
                }

                override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: Error get categories: ${t.message.toString()}")
                    categoriesResponse.postValue(null)
                }
            })
        }
        return categoriesResponse
    }
}