package com.rizrmdhn.kankerdetection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val name: String,
    val id: String?
) : Parcelable