package com.rizrmdhn.kankerdetection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val publishedAt: String,
    val author: String,
    val urlToImage: String,
    val description: String,
    val source: Source,
    val title: String,
    val url: String,
    val content: String
) : Parcelable