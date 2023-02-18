package com.rabotyagi.onboarding.hackaton.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Role(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
):Parcelable