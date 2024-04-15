package com.rizrmdhn.kankerdetection.data.source.local

import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import com.rizrmdhn.kankerdetection.data.source.local.room.ResultHistoryDao

class LocalDataSource(
    private val historyDao: ResultHistoryDao
) {
    fun getAllHistory() = historyDao.getAll()
    fun getHistoryById(id: Int) = historyDao.getById(id)
    suspend fun insertHistory(resultHistory: ResultHistoryEntity) = historyDao.insert(resultHistory)
    fun deleteHistoryById(id: Int) = historyDao.deleteById(id)
}