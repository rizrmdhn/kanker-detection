package com.rizrmdhn.kankerdetection.domain.usecase

import com.rizrmdhn.kankerdetection.data.Resource
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import kotlinx.coroutines.flow.Flow

interface KankerDetectionUseCase {
    fun getAllHistory(): Flow<Resource<List<ResultHistoryEntity>>>
    fun getHistoryById(id: Int): Flow<Resource<ResultHistoryEntity>>
    suspend fun insertHistory(resultHistory: ResultHistoryEntity)
    fun deleteHistoryById(id: Int)
}