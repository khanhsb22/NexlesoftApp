package com.example.nexlesoftapp.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitBuilder {
    companion object {
        private const val URL = "http://streaming.nexlesoft.com:4000/api/"
        private lateinit var jsonApi: JsonApi

        private fun buildOkHttpClient(token: String): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newRequest: Request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    // .header("Authorization", "Bearer Token $token")
                    return chain.proceed(newRequest)
                }
            }).build()
        }


        fun getJSONApi(): JsonApi {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
            jsonApi = retrofit.create(JsonApi::class.java)
            return jsonApi
        }

        fun getJSONApi(token: String): JsonApi {
            val gson = GsonBuilder().setLenient().create()
            val client = buildOkHttpClient(token)
            val retrofit = Retrofit.Builder().baseUrl(URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
            jsonApi = retrofit.create(JsonApi::class.java)
            return jsonApi
        }
    }
}