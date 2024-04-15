package com.rizrmdhn.kankerdetection.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity

@Database(entities = [ResultHistoryEntity::class], version = 2)
abstract class KankerDetectionDatabase : RoomDatabase() {
    abstract fun resultHistoryDao(): ResultHistoryDao
}