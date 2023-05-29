package com.example.nexlesoftapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.nexlesoftapp.factory.MainViewModelFactory
import com.example.nexlesoftapp.network.JsonApi
import com.example.nexlesoftapp.network.RetrofitBuilder
import com.example.nexlesoftapp.repository.MainRepository
import com.example.nexlesoftapp.viewmodel.MainViewModel
import javax.inject.Inject

class SetupConfig @Inject constructor() {
    private lateinit var mainViewModel: MainViewModel

    fun setupNetwork(): JsonApi {
        return RetrofitBuilder.getJSONApi()
    }

    fun setupNetworkWithToken(token: String): JsonApi {
        return RetrofitBuilder.getJSONApi(token)
    }

    fun initViewModel(activity: AppCompatActivity, _jsonApi: JsonApi): MainViewModel {
        mainViewModel = ViewModelProvider(
            activity,
            MainViewModelFactory(MainRepository(_jsonApi))
        )[MainViewModel::class.java]
        return mainViewModel
    }
}