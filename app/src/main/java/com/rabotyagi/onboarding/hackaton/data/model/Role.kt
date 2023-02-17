package com.rabotyagi.onboarding.hackaton.data.model

import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)