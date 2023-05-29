package com.example.nexlesoftapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryModel : java.io.Serializable {
    @SerializedName("_id")
    @Expose
    var id: String = ""

    @SerializedName("name")
    @Expose
    var name: String = ""

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "[id: ${this.id}, name: ${this.name}]"
    }
}