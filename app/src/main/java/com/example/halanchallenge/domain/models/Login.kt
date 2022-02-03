package com.example.halanchallenge.domain.models

import com.google.gson.annotations.SerializedName

data class Login (
    @SerializedName("username") val userName: String,
    @SerializedName("password") val password: String
)

