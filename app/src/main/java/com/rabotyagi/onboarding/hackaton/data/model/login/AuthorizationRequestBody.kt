package com.rabotyagi.onboarding.hackaton.data.model.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorizationRequestBody(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
) : Parcelable