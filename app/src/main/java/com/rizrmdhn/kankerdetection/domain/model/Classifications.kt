package com.rizrmdhn.kankerdetection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Classifications(
    val categories: List<Category>,
    val headIndex: Int
) : Parcelable