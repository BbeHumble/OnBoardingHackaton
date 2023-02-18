package com.rabotyagi.onboarding.hackaton.entity.selector

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SingleSelectionItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    val type: Int
) : Parcelable {

}