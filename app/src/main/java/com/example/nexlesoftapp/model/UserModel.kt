package com.example.nexlesoftapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel constructor(firstName: String, lastName: String, email: String, password: String) {
    @SerializedName("firstName")
    @Expose
    var firstName = ""

    @SerializedName("lastName")
    @Expose
    var lastName = ""

    @SerializedName("email")
    @Expose
    var email = ""

    @SerializedName("password")
    @Expose
    var password = ""

    init {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
    }

}