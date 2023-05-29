package com.example.nexlesoftapp.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignupResponse : java.io.Serializable {
    @SerializedName("_id")
    @Expose
    var _id = ""

    @SerializedName("email")
    @Expose
    var email = ""

    @SerializedName("admin")
    @Expose
    var admin = ""

    @SerializedName("firstName")
    @Expose
    var firstName = ""

    @SerializedName("lastName")
    @Expose
    var lastName = ""

    @SerializedName("createdAt")
    @Expose
    var createdAt = ""

    @SerializedName("updatedAt")
    @Expose
    var updatedAt = ""

    @SerializedName("__v")
    @Expose
    var __v = ""

    @SerializedName("displayName")
    @Expose
    var displayName = ""

    @SerializedName("token")
    @Expose
    var token = ""

    @SerializedName("refreshToken")
    @Expose
    var refreshToken = ""

    override fun toString(): String {
        return "SignupResponse(_id='$_id', email='$email', admin='$admin', firstName='$firstName', lastName='$lastName', createdAt='$createdAt', updatedAt='$updatedAt', __v='$__v', displayName='$displayName', token='$token', refreshToken='$refreshToken')"
    }


}