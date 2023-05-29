package com.example.nexlesoftapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nexlesoftapp.model.UserModel
import com.example.nexlesoftapp.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    fun signupUser(userInfo: UserModel) = mainRepository.signupUser(userInfo)
    fun getCategories(pageSize: Int, pageNumber: Int) =
        mainRepository.getCategories(pageSize, pageNumber)
}