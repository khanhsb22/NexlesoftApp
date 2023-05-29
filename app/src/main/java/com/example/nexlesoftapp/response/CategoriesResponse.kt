package com.example.nexlesoftapp.response

import com.example.nexlesoftapp.model.CategoryModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoriesResponse {
    @SerializedName("categories")
    @Expose
    var categories: List<CategoryModel>? = null
}