package com.example.halanchallenge.domain.repository.remote.models

import com.google.gson.annotations.SerializedName

data class Login (
    @SerializedName("username") val userName: String,
    @SerializedName("password") val password: String
)

