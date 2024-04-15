package com.rizrmdhn.kankerdetection.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultHistory(
    val id: Int,
    val imageUri: String,
    val result: String,
    val createdAt: String
) : Parcelable