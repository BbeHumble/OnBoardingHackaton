package com.rabotyagi.onboarding.hackaton.data.model

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("departmentId")
    val departmentId: Int
)