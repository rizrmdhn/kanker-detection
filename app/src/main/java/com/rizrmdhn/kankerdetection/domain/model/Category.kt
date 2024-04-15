package com.rizrmdhn.kankerdetection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String,
    val displayName: String,
    val score: Double,
    val index: Int
) : Parcelable
