package com.example.api.data.model

import com.google.gson.annotations.SerializedName

data class PostBaru(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)
