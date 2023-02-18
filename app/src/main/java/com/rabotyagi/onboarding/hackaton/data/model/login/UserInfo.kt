package com.rabotyagi.onboarding.hackaton.data.model.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("department")
    val department: Department,
    @SerializedName("role")
    val role: Role,
): Parcelable