package com.example.halanchallenge.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T> (
    @SerializedName("status") @Expose var status : Boolean,
    @SerializedName("message") @Expose var message : String,
    @SerializedName("products") @Expose var data : List<T>? = null
)

data class WrappedResponse<T> (
    @SerializedName("message") @Expose var message : String,
    @SerializedName("status") @Expose var status : String,
    @SerializedName("token") @Expose var token : String,
    @SerializedName("profile") @Expose var data : T? = null
)