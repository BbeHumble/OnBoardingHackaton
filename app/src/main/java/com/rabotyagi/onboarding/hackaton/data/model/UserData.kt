package com.rabotyagi.onboarding.hackaton.data.model

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val roles: List<String>,
    @SerializedName("department")
    val department:Department
)