package com.rabotyagi.onboarding.hackaton.data.model

import com.google.gson.annotations.SerializedName

data class File(
    @SerializedName("title")
    val title: String,
    @SerializedName("file_url")
    val fileUrl: String
)
