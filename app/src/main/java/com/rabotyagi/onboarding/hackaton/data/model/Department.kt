package com.rabotyagi.onboarding.hackaton.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Department(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
):Parcelable