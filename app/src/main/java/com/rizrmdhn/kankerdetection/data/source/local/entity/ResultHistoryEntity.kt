package com.rizrmdhn.kankerdetection.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "result_history")
class ResultHistoryEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @field:ColumnInfo(name = "image_uri")
    val imageUri: ByteArray,

    @field:ColumnInfo(name = "result")
    val result: String,

    @field:ColumnInfo(name = "created_at")
    val createdAt: String
) : Parcelable